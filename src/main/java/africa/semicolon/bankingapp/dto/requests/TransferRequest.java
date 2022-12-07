package africa.semicolon.bankingapp.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @NotNull
    private String accountNumber;
    @NotNull
    private String accountPin;
    @Positive
    private BigDecimal withdrawalAmount;
    @NotNull
    private String accountToBeTransferredInto;
    @Positive
    private BigDecimal amount;

}
