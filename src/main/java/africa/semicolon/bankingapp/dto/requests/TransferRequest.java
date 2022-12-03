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
public class TransferRequest {
    private String accountNumber;
    private String accountPin;
    private BigDecimal withdrawalAmount;
    private String accountToBeTransferredInto;
    private BigDecimal amount;
    private BigDecimal accountBalance;
}
