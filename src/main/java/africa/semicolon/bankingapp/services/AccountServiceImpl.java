package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.DepositResponse;
import africa.semicolon.bankingapp.exceptions.AccountAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.AccountDoesNotExistException;
import africa.semicolon.bankingapp.exceptions.DepositAmountDoesNotExistException;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Transaction;
import africa.semicolon.bankingapp.model.TransactionType;
import africa.semicolon.bankingapp.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service

public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private  ModelMapper modelMapper;


    @Override
    public AccountInfoResponse createAccount(CreateAccountRequest request) {
    validateAccount(request);
    validateAccountBalance(request);
    Account account = buildAccountFrom(request);
    Account savedAccount = accountRepository.save(account);
    return modelMapper.map(savedAccount,AccountInfoResponse.class);


    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public DepositResponse deposit(DepositRequest depositRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(depositRequest.getAccountNumber()).get();
        checkIfAccountExist(foundAccount);
        validateDeposit(depositRequest);
        BigDecimal balance = foundAccount.getAccountBalance().add(BigDecimal.valueOf(depositRequest.getAmount()));
        foundAccount.setAccountBalance(balance);
        Transaction creditTransaction = createDepositTransaction(depositRequest, balance,foundAccount);
        foundAccount.getTransactions().add(creditTransaction);
        Account accountSaved = accountRepository.save(foundAccount);
        return modelMapper.map(accountSaved,DepositResponse.class);

    }

    private Transaction createDepositTransaction(DepositRequest depositRequest, BigDecimal balance, Account foundAccount) {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(generateTransactionId(foundAccount));
    transaction.setTransactionDate(LocalDate.now());
    transaction.setTransactionType(TransactionType.DEPOSIT);
    transaction.setAmount(BigDecimal.valueOf(depositRequest.getAmount()));
    transaction.setAccountBalance(balance);
    transaction.setNarration("Deposit of"+depositRequest.getAmount()+"was made into the account");
    return transaction;
    }

    private String generateTransactionId(Account account) {
        return String.valueOf(account.getTransactions().size());
    }

    private void validateDeposit(DepositRequest depositRequest) {
        if (depositRequest.getAmount() < 100 ){
            throw new DepositAmountDoesNotExistException("This is not within the amount you can deposit",404);
        }
    }

    private void validateAccountBalance(CreateAccountRequest request) {
        if(request.getInitialDeposit() < 1000){
            throw new DepositAmountDoesNotExistException("Amount cannot be deposited ",404);
        }
    }


    private void validateAccount(CreateAccountRequest request) {
        if(accountRepository.findAccountByEmail(request.getEmail()).isPresent()){
            throw new AccountAlreadyExistException("Account with the email already exist",404);
        }
    }
    private Account buildAccountFrom(CreateAccountRequest createAccountRequest){
        return Account.builder()
                .accountName(createAccountRequest.getAccountName())
                .accountPassword(createAccountRequest.getAccountPassword())
                .accountBalance(BigDecimal.valueOf(createAccountRequest.getInitialDeposit()))
                .email(createAccountRequest.getEmail())
                .accountNumber(generateAccountNumber())
                .transactions(new HashSet<>())
                .build();
    }
    private String generateAccountNumber(){
        return "00"+ UUID.randomUUID().toString().substring(0,8);
    }
    private void checkIfAccountExist(Account foundAccount){
        if(foundAccount == null){
            throw new AccountDoesNotExistException("Account not found");
        }
    }
}
