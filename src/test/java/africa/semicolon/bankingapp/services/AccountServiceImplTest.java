package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void testToCreateAnAccount() throws AccountException {
        createCustomerAccount();
        assertEquals(15,accountService.getAllAccounts().size());

    }

    private AccountInfoResponse createCustomerAccount() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountName("Ara");
        createAccountRequest.setEmail("ara@gmail.com");
        createAccountRequest.setAccountPassword("1111");
        createAccountRequest.setInitialDeposit(1000.00);


        return accountService.createAccount(createAccountRequest);
    }
    @Test
    public void testThatUserDeposit(){
//    createCustomerAccount();
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("0071d84a9c");
        depositRequest.setAmount(600.00);
        TransactionResponse transactionResponse = accountService.deposit(depositRequest);
        assertEquals(1600.00,transactionResponse.getAccountBalance());
    }



}