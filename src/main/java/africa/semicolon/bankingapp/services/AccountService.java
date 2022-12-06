package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.*;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface AccountService {

   TransactionResponse deposit(DepositRequest depositRequest);
   TransactionResponse withdraw(WithdrawalRequest withdrawalRequest);

    BigDecimal getAccountBalance(AccountBalanceRequest accountbalanceRequest);

    TransactionResponse transfer(TransferRequest transferRequest);

    List<Account> getAllAccounts();

    Account findAccountByEmail(String email) throws AccountException;
}
