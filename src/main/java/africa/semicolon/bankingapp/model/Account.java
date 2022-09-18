package africa.semicolon.bankingapp.model;

import lombok.*;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;

import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "user_sequence")
    private Long id;
    private String accountNumber;
    private String accountName;
    private String accountPassword;
    private BigDecimal accountBalance;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;


    public Account(String accountNumber, String accountName, String accountPassword, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
    }
}
