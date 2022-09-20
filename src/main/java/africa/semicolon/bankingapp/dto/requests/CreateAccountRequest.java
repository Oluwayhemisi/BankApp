package africa.semicolon.bankingapp.dto.requests;

import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    private String accountName;
    private String email;
    private String accountPassword;
    private double initialDeposit;
}
