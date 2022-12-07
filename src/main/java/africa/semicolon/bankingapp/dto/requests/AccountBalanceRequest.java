package africa.semicolon.bankingapp.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceRequest {
    @NotNull
    private String accountNumber;
    @NotNull
    private String accountPin;
}
