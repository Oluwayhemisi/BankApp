package africa.semicolon.bankingapp.controller;


import africa.semicolon.bankingapp.dto.requests.AuthToken;
import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.LoginRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfile;
import africa.semicolon.bankingapp.dto.responses.ApiResponse;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.dto.responses.DeleteCustomerResponse;
import africa.semicolon.bankingapp.dto.responses.UpdateProfileResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.exceptions.CustomerException;
import africa.semicolon.bankingapp.model.Customer;
import africa.semicolon.bankingapp.security.security.jwt.TokenProvider;
import africa.semicolon.bankingapp.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/customer")
@AllArgsConstructor
@Slf4j
public class CustomerController {
   private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;


    @PostMapping("create/")
    public ResponseEntity<?> createCustomer( @RequestBody CreateCustomerRequest createCustomerRequest){
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(createCustomerRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Transaction successful")
                    .status("success")
                    .data(createCustomerResponse)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) throws AccountException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = tokenProvider.generateJWTToken(authentication);
        Customer customer = customerService.findCustomerByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token,customer.getId()), HttpStatus.OK);

    }

    @RequestMapping("/verify/{token}")
    public ModelAndView verify(@PathVariable("token") String token) throws CustomerException {
        customerService.verifyUser(token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("verification_success");
        return modelAndView;
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/update")
    public  ResponseEntity<?> updateCustomerProfile (@RequestBody UpdateCustomerProfile updateCustomerProfile){

        UpdateProfileResponse updateProfileResponse = customerService.updateCustomerProfile(updateCustomerProfile.getEmail(), updateCustomerProfile);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Profile updated")
                .status("success")
                .data(updateProfileResponse)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public  ResponseEntity<?> deleteCustomer (@RequestBody String email) throws CustomerException {
        DeleteCustomerResponse deleteCustomerResponse = customerService.deleteCustomer(email);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Transaction successful")
                .status("success")
                .data(deleteCustomerResponse)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
