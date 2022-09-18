package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
