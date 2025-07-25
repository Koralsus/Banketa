package com.banketa.banketa.DTO;

import com.banketa.banketa.Entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDateTime createdAt;
    private UserRole role;
}
