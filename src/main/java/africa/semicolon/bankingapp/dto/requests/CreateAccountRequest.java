package africa.semicolon.bankingapp.dto.requests;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;



@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    @NotNull
    private String accountName;
    @NotNull
    private String email;
    @NotNull
    private String accountPin;
    @Positive
    private BigDecimal initialDeposit;
}
