package africa.semicolon.bankingapp.dto.responses;

import africa.semicolon.bankingapp.model.Account;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class GetAccountInfoResponse {
    private Account account;
    private String message;
}
