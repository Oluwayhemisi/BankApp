package africa.semicolon.bankingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.config.web.servlet.oauth2.login.OAuth2LoginSecurityMarker;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate transactionDate;
    private String transactionType;
    private String narration;
    private BigDecimal amount;
    private BigDecimal accountBalance;

    @JsonIgnore
    @ManyToMany(mappedBy = "transactions")
    private Set<Account> account = new HashSet<>();
}
