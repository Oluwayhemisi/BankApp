package africa.semicolon.bankingapp.dto.responses;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private String status;
    private Object data;
    private int statusCode;
}
