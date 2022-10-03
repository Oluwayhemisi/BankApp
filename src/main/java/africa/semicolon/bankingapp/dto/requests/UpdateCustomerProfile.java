package africa.semicolon.bankingapp.dto.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder

public class UpdateCustomerProfile {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
