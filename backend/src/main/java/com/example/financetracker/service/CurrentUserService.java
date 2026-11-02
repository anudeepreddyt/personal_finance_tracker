package com.example.financetracker.service;
import com.example.financetracker.model.AppUser; import com.example.financetracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Service; import org.springframework.web.server.ResponseStatusException; import org.springframework.http.HttpStatus;
@Service public class CurrentUserService {
 private final UserRepository users; public CurrentUserService(UserRepository users){this.users=users;}
 public AppUser get(){Object p=SecurityContextHolder.getContext().getAuthentication().getPrincipal();String username=String.valueOf(p);return users.findByUsernameIgnoreCase(username).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not found"));}
}
