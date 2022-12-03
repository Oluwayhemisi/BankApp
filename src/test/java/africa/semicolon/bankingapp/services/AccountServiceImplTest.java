package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void testToCreateAnAccount() throws AccountException {
        createCustomerAccount();
        assertEquals(9,accountService.getAllAccounts().size());

    }

    private AccountInfoResponse createCustomerAccount() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountName("Sharon");
        createAccountRequest.setEmail("sharon@gmail.com");
        createAccountRequest.setAccountPin("8888");
        createAccountRequest.setInitialDeposit(new BigDecimal(1000));


        return accountService.createAccount(createAccountRequest);
    }
    @Test
    public void testThatUserDeposit(){
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("4485207602");
        depositRequest.setAmount(new BigDecimal("2000.00"));
        TransactionResponse transactionResponse = accountService.deposit(depositRequest);
        assertEquals((new BigDecimal("5000.00")),transactionResponse.getAccountBalance());


    }


    @Test
      public void testThatUserCanWithdraw(){
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAccountNumber("4485207602");
        withdrawalRequest.setAccountPin("8888");
        withdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
        TransactionResponse transactionResponse = accountService.withdraw(withdrawalRequest);
        assertEquals((new BigDecimal("3000.00")), transactionResponse.getAccountBalance());

    }




}