package africa.semicolon.bankingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private LocalDate transactionDate;
    private String transactionType;
    private String narration;
    private BigDecimal amount;
    private BigDecimal accountBalance;
}
