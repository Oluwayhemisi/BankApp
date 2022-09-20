package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.responses.AccountInfoResponse;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account/")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create/")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        log.info("yes i'm in the controller");
        try{
            AccountInfoResponse accountInfoResponse = accountService.createAccount(createAccountRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("Account created successfully")
                    .data(accountInfoResponse)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("failed")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.valueOf(400));
        }
    }
}
