
package com.example.financetracker.controller;

import com.example.financetracker.model.FinanceTransaction;
import com.example.financetracker.repository.TransactionRepository;
import com.example.financetracker.service.CurrentUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/transactions")
public class ExportController {

 private final TransactionRepository repo;
 private final CurrentUserService current;

 public ExportController(TransactionRepository repo, CurrentUserService current) {
  this.repo = repo;
  this.current = current;
 }

 @GetMapping(value = "/export.csv", produces = "text/csv")
 public ResponseEntity<byte[]> csv() {
  StringBuilder s = new StringBuilder(
          "id,type,amount,category,description,date\n"
  );

  for (FinanceTransaction t :
          repo.findByUserIdOrderByTransactionDateDescCreatedAtDesc(
                  current.get().getId())) {

   s.append(t.getId()).append(',')
           .append(t.getType()).append(',')
           .append(t.getAmount()).append(',')
           .append(q(t.getCategory())).append(',')
           .append(q(t.getDescription() == null ? "" : t.getDescription()))
           .append(',')
           .append(t.getTransactionDate())
           .append('\n');
  }

  return ResponseEntity.ok()
          .header(
                  HttpHeaders.CONTENT_DISPOSITION,
                  "attachment; filename=transactions.csv"
          )
          .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
          .body(s.toString().getBytes(StandardCharsets.UTF_8));
 }

 private String q(String x) {
  if (x == null) {
   x = "";
  }

  return "\"" + x.replace("\"", "\"\"") + "\"";
 }
}
