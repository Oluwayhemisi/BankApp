package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);
    @Query(nativeQuery = true, value = "select * from transaction where account_number =?1 AND transaction_date between Date(?2) AND DATE (?3) ")

    List<Transaction> findTransaction(String accountNumber, LocalDate from, LocalDate to);

}
