package africa.semicolon.bankingapp.model;

import africa.semicolon.bankingapp.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String transactionId;
    private LocalDate transactionDate;
    private TransactionType transactionType;
    private String narration;
    private BigDecimal amount;
    private BigDecimal accountBalance;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "transactions")
    private Set<Account> account = new HashSet<>();
}
