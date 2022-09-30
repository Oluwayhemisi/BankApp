package africa.semicolon.bankingapp.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthToken {
    private String token;
    private Long id;


}
