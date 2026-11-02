package com.example.financetracker.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="app_users")
public class AppUser {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, length=120) private String name;
  @Column(nullable=false, unique=true, length=60) private String username;
  @Column(nullable=false, unique=true, length=180) private String email;
  @Column(name="password_hash", nullable=false, length=100) private String passwordHash;
  @Column(name="created_at", nullable=false) private Instant createdAt=Instant.now();
  public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
  public Instant getCreatedAt(){return createdAt;}
}
