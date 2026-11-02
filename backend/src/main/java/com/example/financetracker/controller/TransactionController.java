package com.example.financetracker.controller;

import com.example.financetracker.dto.TransactionDtos.*; import com.example.financetracker.model.*; import com.example.financetracker.repository.TransactionRepository; import com.example.financetracker.service.CurrentUserService;
import jakarta.validation.Valid; import org.springframework.format.annotation.DateTimeFormat; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate; import java.util.*; import java.util.stream.Collectors;

@RestController @RequestMapping("/api/transactions") public class TransactionController {
 private final TransactionRepository repo;private final CurrentUserService current;
 public TransactionController(TransactionRepository r,CurrentUserService c){repo=r;current=c;}
 @GetMapping public List<Response> list(@RequestParam(required=false) TransactionType type,@RequestParam(required=false) String category,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){
  var u=current.get();List<FinanceTransaction> all=(from!=null&&to!=null)?repo.findByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(u.getId(),from,to):repo.findByUserIdOrderByTransactionDateDescCreatedAtDesc(u.getId());
  return all.stream().filter(t->type==null||t.getType()==type).filter(t->category==null||category.isBlank()||t.getCategory().equalsIgnoreCase(category)).map(this::response).collect(Collectors.toList());
 }
 @GetMapping("/{id}") public Response one(@PathVariable Long id){return response(find(id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Response create(@Valid @RequestBody Request r){FinanceTransaction t=new FinanceTransaction();t.setUser(current.get());apply(t,r);return response(repo.save(t));}
 @PutMapping("/{id}") public Response update(@PathVariable Long id,@Valid @RequestBody Request r){FinanceTransaction t=find(id);apply(t,r);return response(repo.save(t));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.delete(find(id));}
 private FinanceTransaction find(Long id){return repo.findByIdAndUserId(id,current.get().getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Transaction not found"));}
 private void apply(FinanceTransaction t,Request r){t.setType(r.type());t.setAmount(r.amount());t.setCategory(r.category().trim());t.setDescription(r.description());t.setTransactionDate(r.transactionDate());}
 private Response response(FinanceTransaction t){return new Response(t.getId(),t.getType(),t.getAmount(),t.getCategory(),t.getDescription(),t.getTransactionDate());}
}
