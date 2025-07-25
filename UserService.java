package com.banketa.banketa.Service;

import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.DTO.LoginDTO;
import com.banketa.banketa.DTO.RegistrationDTO;
import com.banketa.banketa.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserDTO> findByUsernameContainingIgnoreCase(String partOfUsername);
    List<UserDTO> findByFirstname(String firstname);
    List<UserDTO> findByLastname(String lastname);
    List<UserDTO> findByFirstnameAndLastname(String firstname, String lastname);
    List<UserDTO> findByFirstnameContainingIgnoreCase(String partOfFirstname);
    List<UserDTO> findByLastnameContainingIgnoreCase(String partOfLastname);
    List<UserDTO> findByCreation(LocalDateTime creation);
    List<UserDTO> findByCreationBefore(LocalDateTime creation);
    List<UserDTO> findByCreationAfter(LocalDateTime creation);
    List<UserDTO> findByCreationBetween(LocalDateTime start, LocalDateTime end);
    UserDTO registerUser(RegistrationDTO registrationDTO);
    Optional<UserDTO> loginUser(LoginDTO loginDTO);
    UserDTO saveUser(UserDTO userDTO);
    void deleteUserById(Long id);
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    Page<UserDTO> findAllPaged(Pageable pageable);
}