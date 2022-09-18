package africa.semicolon.bankingapp.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String accountPassword;
    private BigDecimal initialDeposit;
}
