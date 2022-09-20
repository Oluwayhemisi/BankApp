package africa.semicolon.bankingapp.security.config.token;


import africa.semicolon.bankingapp.exceptions.AccountException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken confirmationToken) {
    confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) throws AccountException {
        return confirmationTokenRepository.findByToken(token).orElseThrow(()-> new AccountException("token not found"));

    }
    public int setConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
