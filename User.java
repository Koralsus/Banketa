package com.banketa.banketa.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="firstname", nullable = false)
    private String firstname;
    @Column(name="middlename")
    private String middlename;
    @Column(name="lastname", nullable = false)
    private String lastname;
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private UserRole role;
}
