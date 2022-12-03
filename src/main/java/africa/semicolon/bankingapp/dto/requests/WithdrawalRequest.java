package africa.semicolon.bankingapp.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequest {
    private String accountNumber;
    private String accountPin;
    private BigDecimal withdrawalAmount;
}
