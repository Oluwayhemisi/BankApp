package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);
@Query("SELECT a FROM Transaction where transaction_date.to <= :transaction_date.from ")
    List<Transaction> findByTransactionDate(@Param("to")LocalDate to, @Param("from") LocalDate from, String accountNumber);
}
