package com.example.financetracker.dto;
import com.example.financetracker.model.TransactionType; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDate;
public class TransactionDtos {
 public record Request(@NotNull TransactionType type,@NotNull @DecimalMin(value="0.01") @Digits(integer=12,fraction=2) BigDecimal amount,@NotBlank @Size(max=80) String category,@Size(max=500) String description,@NotNull LocalDate transactionDate){}
 public record Response(Long id,TransactionType type,BigDecimal amount,String category,String description,LocalDate transactionDate){}
}
