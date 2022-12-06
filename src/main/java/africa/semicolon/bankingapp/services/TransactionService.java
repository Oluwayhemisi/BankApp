package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createWithdrawalTransaction(WithdrawalRequest withdrawalRequest, BigDecimal balance, Account account);

     Transaction createDepositTransaction(DepositRequest depositRequest, BigDecimal balance, Account foundAccount);

    List<Transaction> getAccountStatement( StatementDTo statementDto);
}
