package com.banketa.banketa.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;
    @Column(name="account_number", nullable = false, unique = true, insertable = false, updatable = false)
    private String accountNumber;
    @Column(name="balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
