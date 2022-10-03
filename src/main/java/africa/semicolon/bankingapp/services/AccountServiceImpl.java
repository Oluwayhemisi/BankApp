package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.*;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Role;
import africa.semicolon.bankingapp.model.Transaction;
import africa.semicolon.bankingapp.model.enums.TransactionType;
import africa.semicolon.bankingapp.repository.AccountRepository;
import africa.semicolon.bankingapp.repository.TransactionRepository;
import africa.semicolon.bankingapp.security.security.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TransactionRepository transactionRepository;



    @Override
    public AccountInfoResponse createAccount(CreateAccountRequest request) {
    validateAccount(request);
    validateAccountBalance(request);
    Account account = new Account(request.getAccountName(),request.getEmail(),bCryptPasswordEncoder.encode(request.getAccountPassword()),BigDecimal.valueOf(request.getInitialDeposit()));
    account.setAccountNumber(generateAccountNumber());
    account.setTransactions(new HashSet<>());
    account.setVerified(true);


    Account savedAccount = accountRepository.save(account);
    AccountInfoResponse acc = modelMapper.map(savedAccount,AccountInfoResponse.class);
    String token = tokenProvider.generateTokenForVerification(String.valueOf(savedAccount.getId()));




   acc.setToken(token);
    return acc;

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountByEmail(String email) throws AccountException {
        return accountRepository.findAccountByEmail(email).orElseThrow(()-> new AccountDoesNotExistException("Account with the email already exist"));
    }

    @Override
    @Transactional

    public TransactionResponse deposit(DepositRequest depositRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(depositRequest.getAccountNumber()).get();
        checkIfAccountExist(foundAccount);
        BigDecimal balance = foundAccount.getAccountBalance().add((depositRequest.getAmount()));
        foundAccount.setAccountBalance(balance);
        Transaction creditTransaction = createDepositTransaction(depositRequest, balance,foundAccount);
        log.info("Transaction id is {}", creditTransaction.getTransactionId());
        Transaction savedTransaction = transactionRepository.save(creditTransaction);
        foundAccount.getTransactions().add(savedTransaction);

        Account accountSaved = accountRepository.save(foundAccount);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountBalance(accountSaved.getAccountBalance());
        transactionResponse.setMessage("The amount of "+depositRequest.getAmount()+ " was deposited in your account");
        transactionResponse.setTransactionDate(LocalDateTime.now());
        transactionResponse.setTransactionType(TransactionType.DEPOSIT);
        transactionResponse.setAmount(depositRequest.getAmount());
        transactionResponse.setAccountBalance(balance);
        return transactionResponse;

    }

    @Override
    public TransactionResponse withdraw(WithdrawalRequest withdrawalRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(withdrawalRequest.getAccountNumber()).get();
        checkIfAccountExist(foundAccount);
        validateWithdrawal(withdrawalRequest,foundAccount);
        BigDecimal balance = foundAccount.getAccountBalance().subtract(BigDecimal.valueOf(withdrawalRequest.getWithdrawalAmount()));
        foundAccount.setAccountBalance(balance);
        Transaction debitTransaction = createWithdrawalTransaction(withdrawalRequest, balance, foundAccount);
        foundAccount.getTransactions().add(debitTransaction);
        Account accountTransaction = accountRepository.save(foundAccount);

        return modelMapper.map(accountTransaction,TransactionResponse.class);



    }

    @Override
    public Set<TransactionResponse> getAccountStatement(String accountNumber) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(accountNumber).get();
        checkIfAccountExist(foundAccount);
        Set<Transaction> transactionList = foundAccount.getTransactions();
        return transactionList.stream().map(this::buildTransactionResponse).collect(Collectors.toSet());

    }

    @Override

    public void verifyUser(String token) throws AccountException {
    Claims claims  = tokenProvider.getAllClaimsFromJWTToken(token);
        Function<Claims, String> getSubjectFromClaim = Claims::getSubject;
        Function<Claims, Date> getExpirationDateFromClaim = Claims::getExpiration;
        Function<Claims, Date> getIssuedAtDateFromClaim = Claims::getIssuedAt;

        String accountId = getSubjectFromClaim.apply(claims);
        if(accountId == null){
            throw new AccountException("Account id is not present in verification token", 404);
        }
        Date expiryDate = getExpirationDateFromClaim.apply(claims);
        if(expiryDate == null){
            throw new AccountException("Expiry date not present in verification token",404);
        }
        Date issuedAtDate = getIssuedAtDateFromClaim.apply(claims);
        if(issuedAtDate == null){
            throw new AccountException("Issue At date not present in verification",404);
        }
        if(expiryDate.compareTo(issuedAtDate) > 14.4){
            throw new AccountException("Verification token has already expired",404);
        }

        Account account = findAccountByIdInternal(accountId);
        if(account == null){
            throw new AccountException("Account id does not exist", 404);
        }
        account.setVerified(true);
        accountRepository.save(account);
    }

    private Account findAccountByIdInternal(String accountId) {
        return accountRepository.findById(Long.valueOf(accountId)).orElse(null);
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
//                .transactionDate(formatDateToString(transaction.getTransactionDate()))
//                .transactionType(String.valueOf(transaction.getTransactionType()))
//                .accountBalance(transaction.getAccountBalance().doubleValue())
//                .amount(transaction.getAmount().doubleValue())
                .build();
    }

    private String formatDateToString(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd-MM-yyyy, hh-mm-ss, a");
        return dateTimeFormatter.format(localDate);
    }

    private Transaction createWithdrawalTransaction( WithdrawalRequest withdrawalRequest,BigDecimal balance, Account account) {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(generateTransactionId(account));
//    transaction.setTransactionType(TransactionType.WITHDRAWAL);
    transaction.setTransactionDate(LocalDate.now());
    transaction.setAmount(BigDecimal.valueOf(withdrawalRequest.getWithdrawalAmount()));
    transaction.setAccountBalance(balance);
    transaction.setNarration("withdrawal of "+withdrawalRequest.getAccountNumber()+" was made from your account");

    return transaction;
    }

    private void validateWithdrawal(WithdrawalRequest withdrawalRequest, Account account) {
        if(!account.getAccountPassword().equals(withdrawalRequest.getAccountPassword())){
            throw new IncorrectPasswordException("Incorrect Password");
        }
    }

    private Transaction createDepositTransaction(DepositRequest depositRequest, BigDecimal balance, Account foundAccount) {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(generateTransactionId(foundAccount));
    transaction.setTransactionDate(LocalDate.now());
    transaction.setTransactionType(TransactionType.DEPOSIT);
    transaction.setAmount(depositRequest.getAmount());
    transaction.setAccountBalance(balance);
    transaction.setNarration("Deposit of"+depositRequest.getAmount()+"was made into the account");
    return transaction;
    }

    private String generateTransactionId(Account account) {

        return String.valueOf(account.getTransactions().size() + 1);
    }

    private void validateDeposit(DepositRequest depositRequest) {
//        if (depositRequest.getAmount() < 100 ){
            throw new DepositAmountDoesNotExistException("This is not within the amount you can deposit",404);
//        }
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
//                .accountPassword(createAccountRequest.getAccountPassword())
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


    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(email).orElse(null);
        if(account != null){
            return new org.springframework.security.core.userdetails.User(account.getEmail(),
                    account.getAccountPassword(),getAuthorities(account.getRoles()));
        }
        return  null;
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles){
        return roles.stream().map(
                role ->new SimpleGrantedAuthority(role.getRoleType().name())).collect(Collectors.toSet());
    }
}
