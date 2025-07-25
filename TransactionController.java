package com.banketa.banketa.Controller;

import com.banketa.banketa.DTO.TransactionDTO;
import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.Entity.TransactionType;
import com.banketa.banketa.Entity.UserRole;
import com.banketa.banketa.Service.AccountService;
import com.banketa.banketa.Service.TransactionService;
import com.banketa.banketa.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/search/transactiondate")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByTransactionDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            Authentication authentication
    ) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isAdmin = user.getRole() == UserRole.ADMIN;

        if (isAdmin) {
            return ResponseEntity.ok(transactionService.findByTransactionDate(date));
        } else {
            return ResponseEntity.ok(transactionService.findByUserIdAndTransactionDate(user.getUserId(), date));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TransactionDTO> createTransaction(
            @Valid @RequestBody TransactionDTO transactionDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> accountNumbers = accountService.findByUserId(user.getUserId())
                .stream()
                .map(acc -> acc.getAccountNumber())
                .toList();

        if (transactionDTO.getFromAccount() != null
                && transactionDTO.getFromAccount().getAccountNumber() != null
                && !accountNumbers.contains(transactionDTO.getFromAccount().getAccountNumber())) {
            return ResponseEntity.status(403).build();
        }

        TransactionDTO createdTransaction = transactionService.saveTransaction(transactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionDTO>> getMyTransactions(Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(transactionService.findByUserId(user.getUserId()));
    }

    @GetMapping("/my/type")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionDTO>> getMyTransactionsByType(
            @RequestParam TransactionType type,
            Authentication authentication
    ) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(transactionService.findByUserIdAndTransactionType(user.getUserId(), type));
    }

    @GetMapping("/my/from")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionDTO>> getMyFromTransactions(Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(transactionService.findByUserIdAsSender(user.getUserId()));
    }

    @GetMapping("/my/to")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionDTO>> getMyToTransactions(Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(transactionService.findByUserIdAsReceiver(user.getUserId()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/search/type")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByTransactionType(@RequestParam TransactionType type) {
        return ResponseEntity.ok(transactionService.findByTransactionType(type));
    }

    @GetMapping("/search/amount/greaterthan")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByAmountGreaterThan(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.findByAmountGreaterThan(amount));
    }

    @GetMapping("/search/amount/lessthan")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByAmountLessThan(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.findByAmountLessThan(amount));
    }

    @GetMapping("/search/transactiondate/between")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByTransactionDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(transactionService.findByTransactionDateBetween(start, end));
    }

    @GetMapping("/search/fromaccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByFromAccountNumber(@RequestParam String accountNumber) {
        return ResponseEntity.ok(transactionService.findByFromAccountNumber(accountNumber));
    }

    @GetMapping("/search/toaccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByToAccountNumber(@RequestParam String accountNumber) {
        return ResponseEntity.ok(transactionService.findByToAccountNumber(accountNumber));
    }

    @GetMapping("/search/fromaccount/daterange")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByFromAccountNumberAndDateRange(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(transactionService.findByFromAccountNumberAndTransactionDateBetween(accountNumber, start, end));
    }

    @GetMapping("/search/toaccount/daterange")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByToAccountNumberAndDateRange(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(transactionService.findByToAccountNumberAndTransactionDateBetween(accountNumber, start, end));
    }

    @GetMapping("/search/fromortoaccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDTO>> searchByFromOrToAccountNumber(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber
    ) {
        return ResponseEntity.ok(transactionService.findByFromOrToAccountNumber(fromAccountNumber, toAccountNumber));
    }
}