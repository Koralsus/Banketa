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
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id", nullable = false)
    private Long transactionId;
    @ManyToOne
    @JoinColumn(name="from_account", nullable = false)
    private Account fromAccount;
    @ManyToOne
    @JoinColumn(name="to_account", nullable = false)
    private Account toAccount;
    @Column(name="amount", nullable = false)
    private BigDecimal amount;
    @Column(name="transaction_date", updatable = false)
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type", nullable = false)
    private TransactionType transactionType;
    @Column(name="remarks")
    private String remarks;
}
