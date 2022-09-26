package africa.semicolon.bankingapp.dto.responses;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String message;
    private Double accountBalance;
    private Double amount;
    private String transactionDate;
    private String transactionType;


}
