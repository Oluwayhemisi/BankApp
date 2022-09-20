package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.model.Account;

import java.util.List;

public interface AccountService {
    AccountInfoResponse createAccount(CreateAccountRequest request);

   List<Account> getAllAccounts();
   TransactionResponse deposit(DepositRequest depositRequest);
   TransactionResponse withdraw(WithdrawalRequest withdrawalRequest);

//   public String confirmToken(String token);
}
