package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.*;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.services.AccountService;
import africa.semicolon.bankingapp.services.StatementDTo;
import africa.semicolon.bankingapp.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;



    @PostMapping("deposit/")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest depositRequest){
        try{
            System.out.println(depositRequest.getAccountNumber()+ "iam here");
            TransactionResponse transactionResponse = accountService.deposit(depositRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Transaction successful")
                    .status("success")
                    .data(transactionResponse)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);

        }catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("failed")
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.valueOf(e.getMessage()));
        }
    }
    @PostMapping("withdraw/")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawalRequest withdrawalRequest){
        try{
            TransactionResponse transactionResponse = accountService.withdraw(withdrawalRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Transaction successful")
                    .status("success")
                    .data(transactionResponse)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);

        }catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("failed")
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.valueOf(e.getMessage()));
        }
        }
        @PostMapping("/transfer")
        public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest){
            TransactionResponse transactionResponse = accountService.transfer(transferRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Transaction successful")
                    .status("success")
                    .data(transactionResponse)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);

        }


        @GetMapping("accountbalance/")
        public  ResponseEntity<?> getAccountBalance(@RequestBody AccountBalanceRequest accountBalanceRequest){
            return new ResponseEntity<>(accountService.getAccountBalance(accountBalanceRequest), HttpStatus.OK);
        }
//
    @PostMapping("accountTransaction/")
    public  ResponseEntity<?> getAccountTransaction(@Valid @RequestBody StatementDTo statementDTo){
        return new ResponseEntity<>(transactionService.getAccountStatement(statementDTo), HttpStatus.OK);
    }

    }

