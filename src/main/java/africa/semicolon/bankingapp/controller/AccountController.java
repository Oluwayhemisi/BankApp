package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

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
    }

