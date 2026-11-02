package com.example.financetracker.repository;
import com.example.financetracker.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate; import java.util.*;
public interface TransactionRepository extends JpaRepository<FinanceTransaction,Long> {
 List<FinanceTransaction> findByUserIdOrderByTransactionDateDescCreatedAtDesc(Long userId);
 Optional<FinanceTransaction> findByIdAndUserId(Long id, Long userId);
 List<FinanceTransaction> findByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(Long userId, LocalDate from, LocalDate to);
}
