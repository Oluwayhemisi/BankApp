package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfile;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.exceptions.CustomerAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.CustomerDoesNotExistException;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Customer;
import africa.semicolon.bankingapp.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CustomerServiceImpl implements CustomerService{
   private CustomerRepository customerRepository;
   private ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerRepository.findByEmail(createCustomerRequest.getEmail()).orElseThrow(()-> new CustomerAlreadyExistException("Customer already exist"));
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setLastName(createCustomerRequest.getLastName());
        customer.setEmail(createCustomerRequest.getEmail());
        customer.setPhoneNumber(customer.getPhoneNumber());
        customer.setAccount(createCustomerRequest.getAccount());
        Customer savedCustomer = customerRepository.save(customer);
        CreateCustomerResponse createCustomerResponse = modelMapper.map(savedCustomer, CreateCustomerResponse.class);
        createCustomerResponse.setMessage("Customer's Account has successfully been created");
        createCustomerResponse.setEmail(createCustomerResponse.getEmail());
        return createCustomerResponse;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CreateCustomerResponse updateCustomerProfile(String email,UpdateCustomerProfile updateCustomerProfile) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerDoesNotExistException("Customer does not exist"));
        Customer savedCustomer = modelMapper.map(updateCustomerProfile, Customer.class);
        savedCustomer.setFirstName(customer.getFirstName());
        savedCustomer.setLastName(customer.getLastName());
        savedCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(savedCustomer);

        return modelMapper.map(savedCustomer,CreateCustomerResponse.class);

    }

    @Override
    public Customer findCustomerByEmail(String email) throws AccountException {
        return customerRepository.findByEmail(email).orElseThrow(()-> new AccountException("Customer email not found",404));

    }
}
