package africa.semicolon.bankingapp.repository;

import africa.semicolon.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);
//@Query("SELECT a FROM Transaction where transaction_date.to <= :transaction_date.from ")
//    List<Transaction> findByTransactionDate(LocalDate to,LocalDate from, String accountNumber);
}
