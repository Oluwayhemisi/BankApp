package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
