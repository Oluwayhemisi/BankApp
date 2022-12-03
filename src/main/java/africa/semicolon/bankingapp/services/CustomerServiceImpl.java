package africa.semicolon.bankingapp.services;

import africa.semicolon.bankingapp.dto.requests.CreateCustomerRequest;
import africa.semicolon.bankingapp.dto.requests.UpdateCustomerProfile;
import africa.semicolon.bankingapp.dto.responses.CreateCustomerResponse;
import africa.semicolon.bankingapp.dto.responses.UpdateProfileResponse;
import africa.semicolon.bankingapp.exceptions.AccountException;
import africa.semicolon.bankingapp.exceptions.CustomerAlreadyExistException;
import africa.semicolon.bankingapp.exceptions.CustomerDoesNotExistException;
import africa.semicolon.bankingapp.exceptions.CustomerException;
import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Customer;
import africa.semicolon.bankingapp.model.enums.RoleType;
import africa.semicolon.bankingapp.repository.CustomerRepository;

import africa.semicolon.bankingapp.security.security.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class CustomerServiceImpl implements CustomerService, UserDetailsService {
   private CustomerRepository customerRepository;
   private ModelMapper modelMapper;

   private PasswordEncoder passwordEncoder;

    private TokenProvider tokenProvider;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper,
                               PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;


    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        Optional<Customer> customer1 = customerRepository.findByEmail(createCustomerRequest.getEmail());//.orElseThrow(()-> new CustomerAlreadyExistException("Customer already exist"));
       if (customer1.isPresent()){
           throw new CustomerAlreadyExistException("I DEY");
       }
       Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setLastName(createCustomerRequest.getLastName());
        customer.setEmail(createCustomerRequest.getEmail());
        customer.setPhoneNumber(createCustomerRequest.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(createCustomerRequest.getPassword()));

        Account account = new Account();
        account.setAccountBalance(createCustomerRequest.getCreateAccountRequest().getInitialDeposit());
        account.setAccountNumber(AccountServiceImpl.generateAccountNumber());
        account.setAccountName(createCustomerRequest.getCreateAccountRequest().getAccountName());
        account.setAccountPin(passwordEncoder.encode(createCustomerRequest.getPassword()));
        account.setEmail(createCustomerRequest.getCreateAccountRequest().getEmail());
        customer.setAccount(account);


        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_USER);
        customer.setRoles(roles);


        Customer savedCustomer = customerRepository.save(customer);


        CreateCustomerResponse createCustomerResponse = modelMapper.map(savedCustomer, CreateCustomerResponse.class);
        createCustomerResponse.setMessage("Customer's Account has successfully been created");
        String token = tokenProvider.generateTokenForVerification(String.valueOf(savedCustomer.getId()));
        createCustomerResponse.setToken(token);

        return createCustomerResponse;
    }



    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public UpdateProfileResponse updateCustomerProfile(String email, UpdateCustomerProfile updateCustomerProfile) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerDoesNotExistException("Customer does not exist"));
        Customer savedCustomer = modelMapper.map(updateCustomerProfile, Customer.class);
        savedCustomer.setFirstName(customer.getFirstName());
        savedCustomer.setLastName(customer.getLastName());
        savedCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(savedCustomer);

        return modelMapper.map(savedCustomer,UpdateProfileResponse.class);

    }

    @Override
    public Customer findCustomerByEmail(String email) throws AccountException {
        return customerRepository.findByEmail(email).orElseThrow(()-> new AccountException("Customer email not found",404));

    }

    @Override
    public void verifyUser(String token) throws CustomerException {
        Claims claims = tokenProvider.getAllClaimsFromJWTToken(token);
        Function<Claims, String> getSubjectFromClaim = Claims::getSubject;
        Function<Claims, Date> getExpirationDateFromClaim = Claims::getExpiration;
        Function<Claims, Date> getIssuedAtDateFromClaim = Claims::getIssuedAt;

        String userId = getSubjectFromClaim.apply(claims);
        if (userId == null){
            throw new CustomerException("User id not present in verification token", 404);
        }
        Date expiryDate = getExpirationDateFromClaim.apply(claims);
        if (expiryDate == null){
            throw new CustomerException("Expiry Date not present in verification token", 404);
        }
        Date issuedAtDate = getIssuedAtDateFromClaim.apply(claims);

        if (issuedAtDate == null){
            throw new CustomerException("Issued At date not present in verification token", 404);
        }

        if (expiryDate.compareTo(issuedAtDate) > 14.4 ){
            throw new CustomerException("Verification Token has already expired", 404);
        }

       Customer customer = findUserByIdInternal(userId);
        if (customer == null){
            throw new CustomerException("User id does not exist",404);
        }
        customer.setVerified(true);
        customerRepository.save(customer);
    }

    private Customer findUserByIdInternal(String userId) {
        return customerRepository.findById(Long.valueOf(userId)).orElse(null);
    }


    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer  = customerRepository.findByEmail(email).orElse(null);
        if(customer != null){
            return new org.springframework.security.core.userdetails.User(customer.getEmail(),
                    customer.getPassword(),getAuthorities(customer.getRoles()));
        }
        return null;
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Set<RoleType> roles){
        return roles.stream().map(
                role ->new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }
}
