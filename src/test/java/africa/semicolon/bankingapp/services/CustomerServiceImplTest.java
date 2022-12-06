package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfileRequest;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.exceptions.CustomerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testToCreateCustomerAccount() throws CustomerException {
        createCustomer();
        assertEquals(1,customerService.getAllCustomers().size());

    }
    private CreateCustomerResponse createCustomer() throws CustomerException {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail("addah@gmail.com");
        createCustomerRequest.setFirstName("Addah");
        createCustomerRequest.setLastName("Mahre");
        createCustomerRequest.setPhoneNumber("08088809339");
        createCustomerRequest.setPassword("5555");

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountName("Addah Mahre");
        createAccountRequest.setAccountPin("5555");
        createAccountRequest.setEmail("addah@gmail.com");
        createAccountRequest.setInitialDeposit(new BigDecimal(5000));

        createCustomerRequest.setCreateAccountRequest(createAccountRequest);

       return customerService.createCustomer(createCustomerRequest);
    }


    @Test
    void updateCustomerProfile() {
        UpdateCustomerProfileRequest updateCustomerProfile = new UpdateCustomerProfileRequest();
        updateCustomerProfile.setEmail("addah@gmail.com");
        updateCustomerProfile.setFirstName("Aisha Addah");
        updateCustomerProfile.setLastName("Mahre");
        updateCustomerProfile.setPhoneNumber("08088809339");

        customerService.updateCustomerProfile("addah@gmail.com",updateCustomerProfile);
        assertEquals("Aisha Addah",updateCustomerProfile.getFirstName());
    }

}