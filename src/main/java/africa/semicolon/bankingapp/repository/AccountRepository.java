package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
