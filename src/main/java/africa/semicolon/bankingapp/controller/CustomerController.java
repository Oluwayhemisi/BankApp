package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer/")
@AllArgsConstructor
@Slf4j
public class CustomerController {
   private final CustomerService customerService;
    @PostMapping("create/")
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequest createCustomerRequest){
        try{
            CreateCustomerResponse createCustomerResponse = customerService.createCustomer(createCustomerRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Transaction successful")
                    .status("success")
                    .data(createCustomerResponse)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        }catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("failed")
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.valueOf(e.getMessage()));
        }
        }

}
