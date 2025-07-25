package com.banketa.banketa.DTO;

import com.banketa.banketa.Entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long transactionId;
    private AccountDTO fromAccount;
    private AccountDTO toAccount;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private String remarks;
}
