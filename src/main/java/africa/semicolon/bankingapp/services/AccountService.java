package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.DepositResponse;
import africa.semicolon.bankingapp.exceptions.AccountAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.AccountDoesNotExistException;
import africa.semicolon.bankingapp.exceptions.ExcessWithdrawnAmountException;
import africa.semicolon.bankingapp.model.Account;

import java.util.List;

public interface AccountService {
    AccountInfoResponse createAccount(CreateAccountRequest request);

   List<Account> getAllAccounts();
   DepositResponse deposit(DepositRequest depositRequest);
}
