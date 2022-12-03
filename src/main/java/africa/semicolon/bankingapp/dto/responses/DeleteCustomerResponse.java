package africa.semicolon.bankingapp.dto.responses;


import africa.semicolon.bankingapp.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteCustomerResponse {
    Customer customer;
}
