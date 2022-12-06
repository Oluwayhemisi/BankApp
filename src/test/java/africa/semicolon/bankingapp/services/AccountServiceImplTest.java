package africa.semicolon.bankingapp.services;
import africa.semicolon.bankingapp.dto.requests.AccountBalanceRequest;
import africa.semicolon.bankingapp.dto.requests.DepositRequest;
import africa.semicolon.bankingapp.dto.requests.WithdrawalRequest;
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
    public void testThatUserDeposit() throws AccountException {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("4450680478");
        depositRequest.setAmount(new BigDecimal("2000.00"));
        TransactionResponse transactionResponse = accountService.deposit(depositRequest);
        assertEquals((new BigDecimal("3000.00")),transactionResponse.getAccountBalance());

    }


    @Test
      public void testThatUserCanWithdraw() throws AccountException {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAccountNumber("4450680478");
        withdrawalRequest.setAccountPin("4444");
        withdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
        TransactionResponse transactionResponse = accountService.withdraw(withdrawalRequest);
        assertEquals((new BigDecimal("2000.00")), transactionResponse.getAccountBalance());

    }
    @Test
    public void testThatUserCanTransfer() throws AccountException {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAccountNumber("4467804475");
        withdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
        withdrawalRequest.setAccountPin("2222");
        TransactionResponse transactionResponse = accountService.withdraw(withdrawalRequest);
        assertEquals(new BigDecimal("3000.00"),transactionResponse.getAccountBalance());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("4452720356");
        depositRequest.setAmount(new BigDecimal(1000));
        TransactionResponse transactionResponse1 = accountService.deposit(depositRequest);
        assertEquals(new BigDecimal("3000.00"), transactionResponse1.getAccountBalance());
    }

    @Test
    public void testThatUserCanGetAccountBalance() throws AccountException {
        AccountBalanceRequest accountBalanceRequest = new AccountBalanceRequest();
        accountBalanceRequest.setAccountNumber("4467804475");
        accountBalanceRequest.setAccountPin("2222");
        BigDecimal accountBalance = accountService.getAccountBalance(accountBalanceRequest);
        assertEquals(new BigDecimal("3000.00"),accountBalance);

    }




}