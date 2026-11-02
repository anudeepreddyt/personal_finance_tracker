package com.example.financetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity @Table(name="budgets", uniqueConstraints=@UniqueConstraint(name="uq_budget_user_month_category", columnNames={"user_id","month","category"}))
public class Budget {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id", nullable=false) private AppUser user;
  @Column(nullable=false) private LocalDate month;
  @Column(nullable=false, length=80) private String category="Overall";
  @Column(name="limit_amount", nullable=false, precision=14, scale=2) private BigDecimal limitAmount;
  @Column(name="created_at", nullable=false) private Instant createdAt=Instant.now();
  public Long getId(){return id;} public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;}
  public LocalDate getMonth(){return month;} public void setMonth(LocalDate v){month=v.withDayOfMonth(1);}
  public String getCategory(){return category;} public void setCategory(String v){category=v;}
  public BigDecimal getLimitAmount(){return limitAmount;} public void setLimitAmount(BigDecimal v){limitAmount=v;}
}
