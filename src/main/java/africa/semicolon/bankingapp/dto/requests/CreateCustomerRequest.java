package africa.semicolon.bankingapp.dto.requests;

import africa.semicolon.bankingapp.model.Account;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private CreateAccountRequest createAccountRequest;
}
