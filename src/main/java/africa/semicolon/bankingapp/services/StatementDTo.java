package africa.semicolon.bankingapp.services;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Setter
@Getter
public class StatementDTo {
    @PastOrPresent(message = "date must be present or past")
    private LocalDate from;
    @PastOrPresent(message = "date must be present or past")
    private LocalDate to;
    @NotNull
  private  String accountNumber;
}
