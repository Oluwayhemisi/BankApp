package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Transaction;
import africa.semicolon.bankingapp.model.enums.TransactionType;
import africa.semicolon.bankingapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public Transaction createWithdrawalTransaction(WithdrawalRequest withdrawalRequest, BigDecimal balance, Account account) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(withdrawalRequest.getWithdrawalAmount());
        transaction.setAccountBalance(balance);
        transaction.setNarration("withdrawal of "+withdrawalRequest.getAccountNumber()+" was made from your account");
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction createDepositTransaction(DepositRequest depositRequest, BigDecimal balance, Account foundAccount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAmount(depositRequest.getAmount());
        transaction.setAccountBalance(balance);
        transaction.setNarration("Deposit of "+depositRequest.getAmount()+"was made into the account");
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public List<TransactionResponse> getAccountStatement(String accountNumber) {
//        LocalDate to = LocalDate.now();
//        LocalDate from = LocalDate.now();
//        List<Transaction> transactionListByDate = transactionRepository.findByTransactionDate(to, from,accountNumber);
//        List<Transaction> transactionList = transactionRepository.findByAccountNumber(accountNumber);
//        return transactionListByDate.stream().map(this::buildTransactionResponse).collect(Collectors.toList());
        return null;
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionDate(LocalDateTime.now())
                .transactionType(transaction.getTransactionType())
                .accountBalance(transaction.getAccountBalance())
                .amount(transaction.getAmount())
                .build();
    }
}
