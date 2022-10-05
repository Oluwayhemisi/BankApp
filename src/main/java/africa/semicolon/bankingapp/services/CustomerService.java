package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfile;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.model.Customer;

import java.util.List;


public interface CustomerService {
    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);

    List<Customer> getAllCustomers();
    CreateCustomerResponse updateCustomerProfile(String email,UpdateCustomerProfile updateCustomerProfile);
    Customer findCustomerByEmail(String email) throws AccountException;



}
