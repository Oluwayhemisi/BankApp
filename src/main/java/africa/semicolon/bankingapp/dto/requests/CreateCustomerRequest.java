package africa.semicolon.bankingapp.dto.requests;

import africa.semicolon.bankingapp.model.Account;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private CreateAccountRequest createAccountRequest;
}
