package africa.semicolon.bankingapp.dto.requests;

import africa.semicolon.bankingapp.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequest {
    @NotNull
    private String accountNumber;
    @NotNull
    private String accountPin;
    @Positive
    private BigDecimal withdrawalAmount;

}
