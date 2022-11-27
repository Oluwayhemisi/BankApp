package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.AccountBalanceRequest;
import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface AccountService {
    AccountInfoResponse createAccount(CreateAccountRequest request);

   List<Account> getAllAccounts();

   Account findAccountByEmail(String email) throws AccountException;
   TransactionResponse deposit(DepositRequest depositRequest);
   TransactionResponse withdraw(WithdrawalRequest withdrawalRequest);

    Set<TransactionResponse> getAccountStatement(String accountNumber);
    void verifyUser(String token) throws AccountException;
    BigDecimal getAccountBalance(AccountBalanceRequest accountbalanceRequest);
}
