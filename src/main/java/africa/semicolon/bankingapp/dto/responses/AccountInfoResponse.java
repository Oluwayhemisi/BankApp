package africa.semicolon.bankingapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoResponse {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
