package africa.semicolon.bankingapp.appconfig;


import africa.semicolon.bankingapp.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

@Configuration
public class AppConfiguration {

//    @Bean
//    public AccountRepository accountRepository(){
//        return new AccountRepositoryImpl();
//    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsService;
//    }
}
