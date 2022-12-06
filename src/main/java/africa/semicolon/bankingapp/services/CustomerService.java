package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfileRequest;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.dto.responses.DeleteCustomerResponse;
import africa.semicolon.bankingapp.dto.responses.UpdateProfileResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.exceptions.CustomerException;
import africa.semicolon.bankingapp.model.Customer;

import java.util.List;


public interface CustomerService {
    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) throws CustomerException;

    List<Customer> getAllCustomers();
    UpdateProfileResponse updateCustomerProfile(String email, UpdateCustomerProfileRequest updateCustomerProfile);
    Customer findCustomerByEmail(String email) throws AccountException;

    void verifyUser(String token) throws CustomerException;

    void deleteAllCustomer (Customer customer);
    DeleteCustomerResponse deleteCustomer (String email) throws CustomerException;









}
