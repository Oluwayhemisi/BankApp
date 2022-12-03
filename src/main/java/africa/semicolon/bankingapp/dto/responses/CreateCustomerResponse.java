package africa.semicolon.bankingapp.dto.responses;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class CreateCustomerResponse {
   private String message;
   private String email;
   private String token;

}
