package com.banketa.banketa.Controller;

import com.banketa.banketa.DTO.AuthResponse;
import com.banketa.banketa.DTO.LoginDTO;
import com.banketa.banketa.DTO.RegistrationDTO;
import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.Entity.UserRole;
import com.banketa.banketa.Service.UserService;
import com.banketa.banketa.Repository.UserRepository;
import com.banketa.banketa.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistrationDTO registrationDTO) {
        UserDTO userDTO = userService.registerUser(registrationDTO);

        com.banketa.banketa.Entity.User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after registration"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.loginUser(loginDTO)
                .map(userDTO -> {
                    com.banketa.banketa.Entity.User user = userRepository.findByUsername(userDTO.getUsername())
                            .orElseGet(() -> userRepository.findByEmail(userDTO.getEmail()).orElseThrow(
                                    () -> new RuntimeException("User not found after login")));

                    UserDetails userDetails = org.springframework.security.core.userdetails.User
                            .withUsername(user.getUsername())
                            .password(user.getPassword())
                            .authorities(user.getRole().name())
                            .build();

                    String token = jwtService.generateToken(userDetails);
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}