package africa.semicolon.bankingapp.model;

import lombok.*;



import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;

import java.util.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String accountName;
    private String accountPassword;


    @Email
    private String email;
    private BigDecimal accountBalance;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;


    @ManyToMany
    @JoinTable(name = "account_transactions",
               joinColumns = @JoinColumn(name = "account_identification "),
               inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions = new HashSet<>();


    public Account(String accountNumber, String accountName, String accountPassword, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
    }



}
