package com.banketa.banketa.Controller;

import com.banketa.banketa.DTO.AccountDTO;
import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.Entity.AccountStatus;
import com.banketa.banketa.Entity.AccountType;
import com.banketa.banketa.Service.AccountService;
import com.banketa.banketa.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<AccountDTO>> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        Long userId = getUserIdByUsername(username);
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    private Long getUserIdByUsername(String username) {
        return userService.findByUsername(username)
                .map(UserDTO::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.saveAccount(accountDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        // Your service might need an update method; otherwise, use saveAccount with the right DTO
        AccountDTO updatedAccount = accountService.saveAccount(accountDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/accountnumber")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> searchByAccountNumber(@RequestParam String accountNumber) {
        return accountService.findByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/type")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByAccountType(@RequestParam AccountType type) {
        return ResponseEntity.ok(accountService.findByAccountType(type));
    }

    @GetMapping("/search/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByAccountStatus(@RequestParam AccountStatus status) {
        return ResponseEntity.ok(accountService.findByAccountStatus(status));
    }

    @GetMapping("/search/creationdate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByCreationDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime creation) {
        return ResponseEntity.ok(accountService.findByCreation(creation));
    }

    @GetMapping("/search/creationdate/before")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByCreationBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before) {
        return ResponseEntity.ok(accountService.findByCreationBefore(before));
    }

    @GetMapping("/search/creationdate/after")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByCreationAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after) {
        return ResponseEntity.ok(accountService.findByCreationAfter(after));
    }

    @GetMapping("/search/creationdate/between")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDTO>> searchByCreationBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(accountService.findByCreationBetween(start, end));
    }

    /* @GetMapping("/paged")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<Page<AccountDTO>> getAccountsPaged(@RequestParam(defaultValue = "0") int page,
    //                                                        @RequestParam(defaultValue = "10") int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     return ResponseEntity.ok(accountService.findAllPaged(pageable));
     }*/
}