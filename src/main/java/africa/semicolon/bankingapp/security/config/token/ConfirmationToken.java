package africa.semicolon.bankingapp.security.config.token;


import africa.semicolon.bankingapp.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
    generator = "confirmation_token_sequence")
    private Long id;

    @Column(nullable = false)
    private String token;


    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;



    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expiresAt,
                             Account account) {
        this.token = token;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
        this.account = account;
    }
}
