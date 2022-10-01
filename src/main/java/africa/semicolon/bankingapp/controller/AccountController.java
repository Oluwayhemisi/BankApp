package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.*;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.dto.responses.TransactionResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.security.security.jwt.TokenProvider;
import africa.semicolon.bankingapp.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("signup/")
    public ResponseEntity<?> createAccount(HttpServletRequest request, @RequestBody CreateAccountRequest createAccountRequest) throws AccountException {
    String host = request.getRequestURL().toString();
    int index = host.indexOf("/",host.indexOf("/", host.indexOf("/")) +2);
    host = host.substring(0,index+1);
    log.info("Host -> {}",host);
    AccountInfoResponse accountInfoResponse = accountService.createAccount(createAccountRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("Account created successfully")
                .data(accountInfoResponse)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        }
    @RequestMapping("/verify/{token}")
    public ModelAndView verify(@PathVariable("token") String token)throws AccountException{
    accountService.verifyUser(token);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("verification_success");
    return modelAndView;
    }
    @PostMapping("login/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws AccountException{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateJWTToken(authentication);
        log.info("Token -> {}",token);
        Account account = accountService.findAccountByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token,account.getId()),HttpStatus.OK);
    }
//    @PostMapping("deposit/")
    @PutMapping("deposit/")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest depositRequest){
        try{
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


    }

