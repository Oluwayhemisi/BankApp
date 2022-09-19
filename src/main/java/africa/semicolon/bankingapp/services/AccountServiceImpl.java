package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.exceptions.AccountAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.DepositAmountDoesNotExistException;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    private ModelMapper modelMapper;


    @Override
    public AccountInfoResponse createAccount(CreateAccountRequest request) {
    validateAccount(request);
    validateAccountBalance(request);
    Account account = buildAccountFrom(request);
    Account savedAccount = accountRepository.save(account);
    return modelMapper.map(savedAccount,AccountInfoResponse.class);


    }

    private void validateAccountBalance(CreateAccountRequest request) {
        if(request.getInitialDeposit() < 1000){
            throw new DepositAmountDoesNotExistException("Amount cannot be deposited ",404);
        }
    }


    private void validateAccount(CreateAccountRequest request) {
        if(accountRepository.findAccountByEmail(request.getEmail()) != null){
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
}
