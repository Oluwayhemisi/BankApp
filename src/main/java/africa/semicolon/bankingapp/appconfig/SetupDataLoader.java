package africa.semicolon.bankingapp.appconfig;

import africa.semicolon.bankingapp.model.Account;
import africa.semicolon.bankingapp.model.Customer;
import africa.semicolon.bankingapp.model.enums.RoleType;
import africa.semicolon.bankingapp.repository.AccountRepository;
import africa.semicolon.bankingapp.repository.CustomerRepository;
import africa.semicolon.bankingapp.services.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (customerRepository.findByEmail("ismailoluwayemisi@gmail.com").isEmpty()){
            Customer customer = new Customer("Oluwayemisi", "Ismail","ismailoluwayemisi@gmail.com", passwordEncoder.encode("password1234#"),"07062396366", RoleType.ROLE_ADMIN);
            customer.setCreatedDate(LocalDateTime.now());
            customerRepository.save(customer);
        }
        if(accountRepository.findAccountByEmail("ismailoluwayemisi@gmail.com").isEmpty()){
            Account account = new Account("Ismail Oluwayemisi","ismailoluwayemisi@gmail.com", passwordEncoder.encode( "1111"),new BigDecimal("1000.00"));
            account.setAccountNumber(AccountServiceImpl.generateAccountNumber());
            accountRepository.save(account);
        }
    }
}
