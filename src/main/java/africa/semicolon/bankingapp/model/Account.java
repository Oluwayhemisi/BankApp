package africa.semicolon.bankingapp.model;

import lombok.*;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String accountName;
    private String accountPassword;
    private BigDecimal accountBalance;

    @ManyToMany
    @JoinTable(name = "account_transactions",
               joinColumns = @JoinColumn(name = "account_id "),
               inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions = new HashSet<>();


    public Account(String accountNumber, String accountName, String accountPassword, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
    }
}
