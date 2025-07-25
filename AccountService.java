package com.banketa.banketa.Service;

import com.banketa.banketa.DTO.AccountDTO;
import com.banketa.banketa.Entity.AccountStatus;
import com.banketa.banketa.Entity.AccountType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDTO> findByAccountNumber(String accountNumber);
    List<AccountDTO> findByCreation(LocalDateTime creation);
    List<AccountDTO> findByCreationBefore(LocalDateTime creation);
    List<AccountDTO> findByCreationAfter(LocalDateTime creation);
    List<AccountDTO> findByCreationBetween(LocalDateTime start, LocalDateTime end);
    List<AccountDTO> findByUserId(Long userId); // Use userId (DTO) instead of User entity
    List<AccountDTO> findByAccountType(AccountType accountType);
    List<AccountDTO> findByAccountStatus(AccountStatus accountStatus);
    AccountDTO saveAccount(AccountDTO accountDTO);
    void deleteAccountById(Long id);
    List<AccountDTO> findAll();
    Optional<AccountDTO> findById(Long id);
}