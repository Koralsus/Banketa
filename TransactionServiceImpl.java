package com.banketa.banketa.Service;

import com.banketa.banketa.DTO.TransactionDTO;
import com.banketa.banketa.Entity.Account;
import com.banketa.banketa.Entity.Transaction;
import com.banketa.banketa.Entity.TransactionType;
import com.banketa.banketa.Entity.User;
import com.banketa.banketa.Mapper.TransactionMapper;
import com.banketa.banketa.Repository.AccountRepository;
import com.banketa.banketa.Repository.TransactionRepository;
import com.banketa.banketa.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public List<TransactionDTO> findByTransactionDate(LocalDateTime date) {
        return transactionRepository.findByTransactionDate(date)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> findByUserIdAndTransactionDate(Long userId, LocalDateTime date){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);
        List<Transaction> results = new ArrayList<>();
        for (Account acc : accounts) {
            results.addAll(transactionRepository.findByAccountIdAndTransactionDate(acc.getAccountId(), date));
        }
        return results.stream().map(transactionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByTransactionDateBetween(start, end)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByFromAccountNumber(String accountNumber) {
        Account fromAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("From account not found"));
        return transactionRepository.findByFromAccount(fromAccount)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByToAccountNumber(String accountNumber) {
        Account toAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("To account not found"));
        return transactionRepository.findByToAccount(toAccount)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByFromAccountNumberAndTransactionDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end) {
        Account fromAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("From account not found"));
        return transactionRepository.findByFromAccountAndTransactionDateBetween(fromAccount, start, end)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByToAccountNumberAndTransactionDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end) {
        Account toAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("To account not found"));
        return transactionRepository.findByToAccountAndTransactionDateBetween(toAccount, start, end)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByFromOrToAccountNumber(String fromAccountNumber, String toAccountNumber) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException("From account not found"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException("To account not found"));
        return transactionRepository.findByFromAccountOrToAccount(fromAccount, toAccount)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByTransactionType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByUserIdAndTransactionType(Long userId, TransactionType transactionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);
        List<Long> accountIds = accounts.stream()
                .map(Account::getAccountId)
                .toList();
        List<Transaction> transactions = transactionRepository.findByAccountIdsAndTransactionType(accountIds, transactionType);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public List<TransactionDTO> findByAmountGreaterThan(BigDecimal amount) {
        return transactionRepository.findByAmountGreaterThan(amount)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByAmountLessThan(BigDecimal amount) {
        return transactionRepository.findByAmountLessThan(amount)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);

        if (transactionDTO.getFromAccount() != null && transactionDTO.getFromAccount().getAccountNumber() != null) {
            Account fromAccount = accountRepository.findByAccountNumber(transactionDTO.getFromAccount().getAccountNumber())
                    .orElseThrow(() -> new EntityNotFoundException("From account not found"));
            transaction.setFromAccount(fromAccount);
        }
        if (transactionDTO.getToAccount() != null && transactionDTO.getToAccount().getAccountNumber() != null) {
            Account toAccount = accountRepository.findByAccountNumber(transactionDTO.getToAccount().getAccountNumber())
                    .orElseThrow(() -> new EntityNotFoundException("To account not found"));
            transaction.setToAccount(toAccount);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionDTO> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDTO);
    }

    @Override
    public List<TransactionDTO> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);
        List<Long> accountIds = accounts.stream()
                .map(Account::getAccountId)
                .toList();
        List<Transaction> transactions = transactionRepository.findByAccountIds(accountIds);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public List<TransactionDTO> findByUserIdAsSender(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);
        List<Long> accountIds = accounts.stream()
                .map(Account::getAccountId)
                .toList();
        List<Transaction> transactions = transactionRepository.findByFromAccountIds(accountIds);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public List<TransactionDTO> findByUserIdAsReceiver(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);
        List<Long> accountIds = accounts.stream()
                .map(Account::getAccountId)
                .toList();
        List<Transaction> transactions = transactionRepository.findByToAccountIds(accountIds);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }
}
