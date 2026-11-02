package com.example.financetracker.repository;
import com.example.financetracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate; import java.util.*;
public interface BudgetRepository extends JpaRepository<Budget,Long> {
 List<Budget> findByUserIdAndMonthOrderByCategoryAsc(Long userId, LocalDate month);
 Optional<Budget> findByIdAndUserId(Long id, Long userId);
}
