package com.example.financetracker.dto;
import jakarta.validation.constraints.*;
public class AuthDtos {
 public record RegisterRequest(@NotBlank @Size(max=120) String name,@NotBlank @Size(min=3,max=60) String username,@NotBlank @Email @Size(max=180) String email,@NotBlank @Size(min=8,max=72) String password){}
 public record LoginRequest(@NotBlank String login,@NotBlank String password){}
 public record UserResponse(Long id,String name,String username,String email){}
 public record AuthResponse(String token,UserResponse user){}
}
