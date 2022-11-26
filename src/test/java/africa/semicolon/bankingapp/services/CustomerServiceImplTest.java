package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;
    private AccountService accountService;
    @Test
    void createCustomer() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail("addah@gmail.com");
        createCustomerRequest.setFirstName("Addah");
        createCustomerRequest.setLastName("Mahre");
        createCustomerRequest.setPhoneNumber("08088809339");
        Account accountRequest = new Account();
//        accountRequest.setEmail("ryan@gmail.com");
        accountRequest.setAccountName("Mahre");
        accountRequest.setAccountPassword("0000");
        createCustomerRequest.setAccount(accountRequest);
        customerService.createCustomer(createCustomerRequest);
        assertEquals(1,customerService.getAllCustomers().size());

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