package com.banketa.banketa.Repository;

import com.banketa.banketa.Entity.Account;
import com.banketa.banketa.Entity.Transaction;
import com.banketa.banketa.Entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionDate(LocalDateTime date);
    List<Transaction> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByAccountIdAndTransactionDate(Long accountId, LocalDateTime date);
    List<Transaction> findByFromAccount(Account fromAccount);
    List<Transaction> findByToAccount(Account toAccount);
    List<Transaction> findByFromAccountAndTransactionDateBetween(Account fromAccount, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByToAccountAndTransactionDateBetween(Account toAccount, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
    List<Transaction> findByTransactionType(TransactionType transactionType);
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);
    List<Transaction> findByAmountLessThan(BigDecimal amount);
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountId IN :accountIds OR t.toAccount.accountId IN :accountIds")
    List<Transaction> findByAccountIds(@Param("accountIds") List<Long> accountIds);
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountId IN :accountIds")
    List<Transaction> findByFromAccountIds(@Param("accountIds") List<Long> accountIds);
    @Query("SELECT t FROM Transaction t WHERE t.toAccount.accountId IN :accountIds")
    List<Transaction> findByToAccountIds(@Param("accountIds") List<Long> accountIds);
    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount.accountId IN :accountIds OR t.toAccount.accountId IN :accountIds) AND t.transactionType = :transactionType")
    List<Transaction> findByAccountIdsAndTransactionType(@Param("accountIds") List<Long> accountIds, @Param("transactionType") TransactionType transactionType);
}
