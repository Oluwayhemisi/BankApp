package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {
    private CustomerService customerService;
    private AccountService accountService;
    @Test
    void createCustomer() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail("ryan@gmail.com");
        createCustomerRequest.setFirstName("Ryan");
        createCustomerRequest.setLastName("Bee");
        createCustomerRequest.setPhoneNumber("08088809339");
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setEmail("ryan@gmail.com");
        createAccountRequest.setAccountName("Ryan");
        createAccountRequest.setInitialDeposit(new BigDecimal(1000));
        createAccountRequest.setAccountPassword("0000");
        accountService.createAccount(createAccountRequest);
//        createCustomerRequest.setAccount();

    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void updateCustomerProfile() {
    }

    @Test
    void findCustomerByEmail() {
    }
}