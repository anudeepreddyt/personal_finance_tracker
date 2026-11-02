package com.example.financetracker.controller;

import com.example.financetracker.dto.AuthDtos.*; import com.example.financetracker.model.AppUser; import com.example.financetracker.repository.UserRepository; import com.example.financetracker.security.JwtService; import com.example.financetracker.service.CurrentUserService;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt; private final CurrentUserService current;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j,CurrentUserService c){users=u;encoder=e;jwt=j;current=c;}
 @PostMapping("/register") public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest r){
  if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");
  if(users.existsByUsernameIgnoreCase(r.username()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Username is already taken");
  AppUser u=new AppUser();u.setName(r.name().trim());u.setUsername(r.username().trim());u.setEmail(r.email().trim().toLowerCase());u.setPasswordHash(encoder.encode(r.password()));users.save(u);
  return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwt.create(u.getUsername()),response(u)));
 }
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){
  AppUser u=users.findByEmailIgnoreCase(r.login()).or(()->users.findByUsernameIgnoreCase(r.login())).filter(x->encoder.matches(r.password(),x.getPasswordHash())).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid login or password"));
  return new AuthResponse(jwt.create(u.getUsername()),response(u));
 }
 @GetMapping("/me") public UserResponse me(){return response(current.get());}
 private UserResponse response(AppUser u){return new UserResponse(u.getId(),u.getName(),u.getUsername(),u.getEmail());}
}
