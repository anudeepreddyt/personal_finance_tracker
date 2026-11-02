package com.example.financetracker.controller;

import com.example.financetracker.dto.BudgetDtos.*; import com.example.financetracker.model.*; import com.example.financetracker.repository.*; import com.example.financetracker.service.CurrentUserService;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/budgets") public class BudgetController {
 private final BudgetRepository budgets;private final TransactionRepository tx;private final CurrentUserService current;
 public BudgetController(BudgetRepository b,TransactionRepository t,CurrentUserService c){budgets=b;tx=t;current=c;}
 @GetMapping public List<Response> list(@RequestParam(required=false) LocalDate month){var u=current.get();LocalDate m=(month==null?LocalDate.now():month).withDayOfMonth(1);return budgets.findByUserIdAndMonthOrderByCategoryAsc(u.getId(),m).stream().map(this::response).toList();}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Response create(@Valid @RequestBody Request r){Budget b=new Budget();b.setUser(current.get());apply(b,r);return response(budgets.save(b));}
 @PutMapping("/{id}") public Response update(@PathVariable Long id,@Valid @RequestBody Request r){Budget b=find(id);apply(b,r);return response(budgets.save(b));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){budgets.delete(find(id));}
 private Budget find(Long id){return budgets.findByIdAndUserId(id,current.get().getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Budget not found"));}
 private void apply(Budget b,Request r){b.setMonth(r.month());b.setCategory(r.category().trim());b.setLimitAmount(r.limitAmount());}
 private Response response(Budget b){LocalDate start=b.getMonth().withDayOfMonth(1),end=start.withDayOfMonth(start.lengthOfMonth());BigDecimal spent=tx.findByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(current.get().getId(),start,end).stream().filter(t->t.getType()==TransactionType.EXPENSE).filter(t->b.getCategory().equalsIgnoreCase("Overall")||t.getCategory().equalsIgnoreCase(b.getCategory())).map(FinanceTransaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);BigDecimal remaining=b.getLimitAmount().subtract(spent);return new Response(b.getId(),start,b.getCategory(),b.getLimitAmount(),spent,remaining,remaining.signum()<0);}
}
