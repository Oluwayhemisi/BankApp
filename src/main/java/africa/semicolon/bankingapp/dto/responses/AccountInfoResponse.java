package africa.semicolon.bankingapp.dto.responses;

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoResponse {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
