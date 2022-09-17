package africa.semicolon.bankingapp.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountNumber;
    private String accountName;
    private String accountPassword;
    private BigDecimal accountBalance;
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountName, String accountPassword, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
        transactions = new ArrayList<>();
    }
}
