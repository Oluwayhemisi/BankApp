package africa.semicolon.bankingapp.model;

import lombok.*;



import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String accountName;
    private String accountPin;


    @Email
    private String email;
    private BigDecimal accountBalance;
    private boolean isVerified;





    public Account( String accountName,String email, String accountPin, BigDecimal accountBalance) {
        this.email = email;
        this.accountName = accountName;
        this.accountPin = accountPin;
        this.accountBalance = accountBalance;



    }




}
