package com.example.financetracker.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDate;
public class BudgetDtos {
 public record Request(@NotNull LocalDate month,@NotBlank @Size(max=80) String category,@NotNull @DecimalMin("0.01") @Digits(integer=12,fraction=2) BigDecimal limitAmount){}
 public record Response(Long id,LocalDate month,String category,BigDecimal limitAmount,BigDecimal spent,BigDecimal remaining,boolean exceeded){}
}
