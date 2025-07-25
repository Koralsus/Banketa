package com.banketa.banketa.Repository;

import com.banketa.banketa.Entity.Account;
import com.banketa.banketa.Entity.AccountStatus;
import com.banketa.banketa.Entity.AccountType;
import com.banketa.banketa.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCreation(LocalDateTime creation);
    List<Account> findByCreationBefore(LocalDateTime creation);
    List<Account> findByCreationAfter(LocalDateTime creation);
    List<Account> findByCreationBetween(LocalDateTime start, LocalDateTime end);
    List<Account> findByUser(User user);
    List<Account> findByAccountType(AccountType accountType);
    List<Account> findByAccountStatus(AccountStatus accountStatus);
}
