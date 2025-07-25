package com.banketa.banketa.DTO;

import com.banketa.banketa.Entity.AccountStatus;
import com.banketa.banketa.Entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private UserDTO user;
}
