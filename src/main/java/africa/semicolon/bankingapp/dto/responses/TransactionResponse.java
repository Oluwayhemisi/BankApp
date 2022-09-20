package africa.semicolon.bankingapp.dto.responses;

import lombok.*;

import java.math.BigDecimal;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String message;
    private BigDecimal accountBalance;
}
