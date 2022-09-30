package africa.semicolon.bankingapp.model;

import africa.semicolon.bankingapp.model.enums.RoleType;
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
    private boolean isVerified;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;


    @ManyToMany
    @JoinTable(name = "account_transactions",
               joinColumns = @JoinColumn(name = "account_identification "),
               inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions = new HashSet<>();


    public Account( String accountName,String email, String accountPassword, BigDecimal accountBalance) {
        this.email = email;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
        if(roles ==  null){
            roles = new HashSet<>();
        }
        roles.add(new Role(RoleType.ROLE_USER));
    }




}
