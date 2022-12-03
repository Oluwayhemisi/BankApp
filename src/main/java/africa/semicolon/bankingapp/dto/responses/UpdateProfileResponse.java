package africa.semicolon.bankingapp.dto.responses;

import africa.semicolon.bankingapp.dto.requests.CreateAccountRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class UpdateProfileResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private CreateAccountRequest createAccountRequest;
}
