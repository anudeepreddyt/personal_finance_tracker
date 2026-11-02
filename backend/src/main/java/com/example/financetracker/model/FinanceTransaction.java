package com.example.financetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity @Table(name="finance_transactions", indexes={@Index(name="idx_tx_user_date", columnList="user_id, transaction_date"), @Index(name="idx_tx_user_category", columnList="user_id, category")})
public class FinanceTransaction {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id", nullable=false) private AppUser user;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=10) private TransactionType type;
  @Column(nullable=false, precision=14, scale=2) private BigDecimal amount;
  @Column(nullable=false, length=80) private String category;
  @Column(length=500) private String description;
  @Column(name="transaction_date", nullable=false) private LocalDate transactionDate;
  @Column(name="created_at", nullable=false) private Instant createdAt=Instant.now();
  @Column(name="updated_at", nullable=false) private Instant updatedAt=Instant.now();
  @PreUpdate void touch(){updatedAt=Instant.now();}
  public Long getId(){return id;} public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;}
  public TransactionType getType(){return type;} public void setType(TransactionType v){type=v;}
  public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
  public String getCategory(){return category;} public void setCategory(String v){category=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public LocalDate getTransactionDate(){return transactionDate;} public void setTransactionDate(LocalDate v){transactionDate=v;}
  public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
