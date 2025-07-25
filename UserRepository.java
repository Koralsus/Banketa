package com.banketa.banketa.Repository;

import com.banketa.banketa.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findByUsernameContainingIgnoreCase(String partOfUsername);
    List<User> findByFirstname(String firstname);
    List<User> findByLastname(String lastname);
    List<User> findByFirstnameAndLastname(String firstname, String lastname);
    List<User> findByFirstnameContainingIgnoreCase(String partOfFirstname);
    List<User> findByLastnameContainingIgnoreCase(String partOfLastname);
    List<User> findByCreation(LocalDateTime creation);
    List<User> findByCreationBefore(LocalDateTime creation);
    List<User> findByCreationAfter(LocalDateTime creation);
    List<User> findByCreationBetween(LocalDateTime start, LocalDateTime end);
}
