package africa.semicolon.bankingapp.services;
import africa.semicolon.bankingapp.dto.requests.*;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.*;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Transaction;
import africa.semicolon.bankingapp.model.enums.TransactionType;
import africa.semicolon.bankingapp.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

 private final    TransactionService transactionService;


    @Override
    @Transactional
    public TransactionResponse deposit(DepositRequest depositRequest) throws AccountException {
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
    public TransactionResponse withdraw(WithdrawalRequest withdrawalRequest) throws AccountException {
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
    public BigDecimal getAccountBalance(AccountBalanceRequest accountbalanceRequest) throws AccountException {
        Account account = accountRepository.findAccountByAccountNumber(accountbalanceRequest.getAccountNumber()).orElseThrow(()-> new AccountException("Account does not exist exception",404));
        validateBalancePassword( accountbalanceRequest,account);
        return account.getAccountBalance();
    }


    @Override
    public TransactionResponse transfer(TransferRequest transferRequest) throws AccountException {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(transferRequest.getWithdrawalAmount());
        withdrawalRequest.setAccountPin(transferRequest.getAccountPin());
        withdrawalRequest.setAccountNumber(transferRequest.getAccountNumber());
        TransactionResponse transactionResponseFirst =    withdraw(withdrawalRequest);

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber(transferRequest.getAccountToBeTransferredInto());
        depositRequest.setAmount(transferRequest.getAmount());
      deposit(depositRequest);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setMessage(transferRequest.getAmount()+" was been debited from "+ transferRequest.getAccountNumber());
        transactionResponse.setTransactionDate(LocalDateTime.now());
        transactionResponse.setTransactionType(TransactionType.DEBIT);
        transactionResponse.setAmount(transferRequest.getAmount());
        transactionResponse.setAccountBalance(transactionResponseFirst.getAccountBalance());
        return transactionResponse;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountByEmail(String email) throws AccountException {
        return accountRepository.findAccountByEmail(email).orElseThrow(()-> new AccountException("Account with the email already exist",404));
    }


    private void validateBalancePassword(AccountBalanceRequest accountbalanceRequest, Account account) throws AccountException {
        log.info("Database password is {}",account.getAccountPin());

        if(!passwordEncoder.matches(accountbalanceRequest.getAccountPin(), account.getAccountPin())){
            log.info("Request password is {}", passwordEncoder.encode(accountbalanceRequest.getAccountPin()));
            throw new AccountException("Incorrect Password",404);
        }
    }


    private void validatePin(WithdrawalRequest withdrawalRequest, Account account) throws AccountException {
        log.info("Database password is {}",account.getAccountPin());

        if(!passwordEncoder.matches(withdrawalRequest.getAccountPin(), account.getAccountPin())){
            log.info("Request password is {}", passwordEncoder.encode(withdrawalRequest.getAccountPin()));
            throw new AccountException("Incorrect Password",404);
        }
    }

    private void validateAccountBalanceBeforeWithdrawal(Account account, WithdrawalRequest withdrawalRequest) throws AccountException {
        if(account.getAccountBalance().compareTo(withdrawalRequest.getWithdrawalAmount()) < 0){
            throw new AccountException("Insufficient amount",404);
        }
    }

    public static String generateAccountNumber(){
       String id = String.valueOf( UUID.randomUUID().getLeastSignificantBits()).substring(1,9);
       return "44"+id;
    }

    private void checkIfAccountExist(Account foundAccount) throws AccountException {
        if(foundAccount == null){
            throw new AccountException("Account not found",404);
        }
    }
}
