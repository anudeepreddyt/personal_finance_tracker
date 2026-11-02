package com.example.financetracker.repository;
import com.example.financetracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<AppUser,Long> {
 Optional<AppUser> findByEmailIgnoreCase(String email); Optional<AppUser> findByUsernameIgnoreCase(String username);
 boolean existsByEmailIgnoreCase(String email); boolean existsByUsernameIgnoreCase(String username);
}
