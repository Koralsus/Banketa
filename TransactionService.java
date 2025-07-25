package com.banketa.banketa.Service;

import com.banketa.banketa.DTO.TransactionDTO;
import com.banketa.banketa.Entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<TransactionDTO> findByTransactionDate(LocalDateTime date);
    List<TransactionDTO> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
    List<TransactionDTO> findByUserIdAndTransactionDate(Long userId, LocalDateTime date);
    List<TransactionDTO> findByFromAccountNumber(String accountNumber);
    List<TransactionDTO> findByToAccountNumber(String accountNumber);
    List<TransactionDTO> findByFromAccountNumberAndTransactionDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end);
    List<TransactionDTO> findByToAccountNumberAndTransactionDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end);
    List<TransactionDTO> findByFromOrToAccountNumber(String fromAccountNumber, String toAccountNumber);
    List<TransactionDTO> findByTransactionType(TransactionType transactionType);
    List<TransactionDTO> findByUserIdAndTransactionType(Long userId, TransactionType transactionType);
    List<TransactionDTO> findByAmountGreaterThan(BigDecimal amount);
    List<TransactionDTO> findByAmountLessThan(BigDecimal amount);
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);
    void deleteTransactionById(Long id);
    List<TransactionDTO> findAll();
    Optional<TransactionDTO> findById(Long id);
    List<TransactionDTO> findByUserId(Long userId);
    List<TransactionDTO> findByUserIdAsSender(Long userId);
    List<TransactionDTO> findByUserIdAsReceiver(Long userId);
}