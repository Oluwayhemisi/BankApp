package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.exceptions.AccountAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.AccountDoesNotExistException;
import africa.semicolon.bankingapp.exceptions.ExcessWithdrawnAmountException;

public interface AccountService {
    public AccountInfoResponse createAccount(CreateAccountRequest request);
}
