package africa.semicolon.bankingapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "user_sequence")
    private Long id;


    private String firstName;


    private String lastName;

    @Email
    private String email;


    private String phoneNumber;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
