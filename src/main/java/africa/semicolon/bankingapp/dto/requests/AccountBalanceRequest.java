package africa.semicolon.bankingapp.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceRequest {
    private String accountNumber;
    private String accountPassword;
}
