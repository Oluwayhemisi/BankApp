package africa.semicolon.bankingapp.services;
import africa.semicolon.bankingapp.dto.requests.AccountBalanceRequest;
import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.*;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Transaction;
import africa.semicolon.bankingapp.model.enums.TransactionType;
import africa.semicolon.bankingapp.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TransactionService transactionService;



    @Override
    public AccountInfoResponse createAccount(CreateAccountRequest request) {
    Account account = new Account(request.getAccountName(),request.getEmail(), passwordEncoder.encode(request.getAccountPin()),request.getInitialDeposit());
    account.setAccountNumber(generateAccountNumber());
    account.setVerified(true);

    Account savedAccount = accountRepository.save(account);
    AccountInfoResponse acc = modelMapper.map(savedAccount,AccountInfoResponse.class);
    return acc;

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountByEmail(String email)  {
        return accountRepository.findAccountByEmail(email).orElseThrow(()-> new AccountDoesNotExistException("Account with the email already exist"));
    }

    @Override
    @Transactional

    public TransactionResponse deposit(DepositRequest depositRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(depositRequest.getAccountNumber()).get();
        checkIfAccountExist(foundAccount);
        BigDecimal balance = foundAccount.getAccountBalance().add((depositRequest.getAmount()));
        foundAccount.setAccountBalance(balance);
        Account accountSaved = accountRepository.save(foundAccount);
        Transaction creditTransaction = transactionService.createDepositTransaction(depositRequest, balance,foundAccount);
        log.info("Transaction id is {}", creditTransaction.getId());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountBalance(accountSaved.getAccountBalance());
        transactionResponse.setMessage("The amount of "+depositRequest.getAmount()+ " was deposited in your account");
        transactionResponse.setTransactionDate(LocalDateTime.now());
        transactionResponse.setTransactionType(TransactionType.CREDIT);
        transactionResponse.setAmount(depositRequest.getAmount());
        return transactionResponse;

    }

    @Override
    public TransactionResponse withdraw(WithdrawalRequest withdrawalRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(withdrawalRequest.getAccountNumber()).get();
        checkIfAccountExist(foundAccount);
        validatePin(withdrawalRequest,foundAccount);
        validateAccountBalanceBeforeWithdrawal(foundAccount,withdrawalRequest);
        BigDecimal balance = foundAccount.getAccountBalance().subtract(withdrawalRequest.getWithdrawalAmount());
        foundAccount.setAccountBalance(balance);
        Account accountTransaction = accountRepository.save(foundAccount);
        Transaction debitTransaction = transactionService.createWithdrawalTransaction(withdrawalRequest, balance, accountTransaction);


        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setMessage("The amount of "+withdrawalRequest.getWithdrawalAmount()+ " was withdrawn from your account");
        transactionResponse.setTransactionDate(LocalDateTime.now());
        transactionResponse.setTransactionType(debitTransaction.getTransactionType());
        transactionResponse.setAmount(debitTransaction.getAmount());
        transactionResponse.setAccountBalance(debitTransaction.getAccountBalance());
        return transactionResponse;

    }


    @Override
    public BigDecimal getAccountBalance(AccountBalanceRequest accountbalanceRequest) {
        Account account = accountRepository.findAccountByAccountNumber(accountbalanceRequest.getAccountNumber()).orElseThrow(()-> new AccountDoesNotExistException("Account does not exist exception"));
        validateBalancePassword( accountbalanceRequest,account);
        return account.getAccountBalance();
    }



    private void validateBalancePassword(AccountBalanceRequest accountbalanceRequest, Account account) {
        log.info("Database password is {}",account.getAccountPin());

        if(!passwordEncoder.matches(accountbalanceRequest.getAccountPassword(), account.getAccountPin())){
            log.info("Request password is {}", passwordEncoder.encode(accountbalanceRequest.getAccountPassword()));
            throw new IncorrectPasswordException("Incorrect Password");
        }

    }


    private void validatePin(WithdrawalRequest withdrawalRequest, Account account) {
        log.info("Database password is {}",account.getAccountPin());

        if(!passwordEncoder.matches(withdrawalRequest.getAccountPin(), account.getAccountPin())){
            log.info("Request password is {}", passwordEncoder.encode(withdrawalRequest.getAccountPin()));
            throw new IncorrectPasswordException("Incorrect Password");
        }

    }


    private void validateAccountBalanceBeforeWithdrawal(Account account, WithdrawalRequest withdrawalRequest){
        if(account.getAccountBalance().compareTo(withdrawalRequest.getWithdrawalAmount()) < 0){
            throw new InsufficientBalanceException("Insufficient amount");
        }
    }

//    private void validateAccountBalance(CreateAccountRequest request) {
//        if(request.getInitialDeposit().compareTo(new BigDecimal(1000)) < -1){
//            throw new DepositAmountDoesNotExistException("Amount cannot be deposited ",404);
//        }
//    }


    public static String generateAccountNumber(){
       String id = String.valueOf( UUID.randomUUID().getLeastSignificantBits()).substring(1,9);
       return "44"+id;
    }

    private void checkIfAccountExist(Account foundAccount){
        if(foundAccount == null){
            throw new AccountDoesNotExistException("Account not found");
        }
    }

}
