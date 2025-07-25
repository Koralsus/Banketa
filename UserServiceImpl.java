package com.banketa.banketa.Service;

import com.banketa.banketa.Custom.UserAlreadyExistsException;
import com.banketa.banketa.DTO.LoginDTO;
import com.banketa.banketa.DTO.RegistrationDTO;
import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.Entity.User;
import com.banketa.banketa.Mapper.UserMapper;
import com.banketa.banketa.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDTO> findById(Long userId){
        return userRepository.findById(userId)
                .map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email){
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO);
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserDTO> findByUsernameContainingIgnoreCase(String partOfUsername){
        return userRepository.findByUsernameContainingIgnoreCase(partOfUsername)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByFirstname(String firstname){
        return userRepository.findByFirstname(firstname)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByLastname(String lastname){
        return userRepository.findByLastname(lastname)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByFirstnameAndLastname(String firstname, String lastname){
        return userRepository.findByFirstnameAndLastname(firstname, lastname)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByFirstnameContainingIgnoreCase(String partOfFirstname){
        return userRepository.findByFirstnameContainingIgnoreCase(partOfFirstname)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByLastnameContainingIgnoreCase(String partOfLastname){
        return userRepository.findByLastnameContainingIgnoreCase(partOfLastname)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByCreation(LocalDateTime creation){
        return userRepository.findByCreation(creation)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByCreationBefore(LocalDateTime creation){
        return userRepository.findByCreationBefore(creation)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByCreationAfter(LocalDateTime creation){
        return userRepository.findByCreationAfter(creation)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByCreationBetween(LocalDateTime start, LocalDateTime end){
        return userRepository.findByCreationBetween(start, end)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO registerUser(RegistrationDTO registrationDTO){
        if (existsByEmail(registrationDTO.getEmail())){
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (existsByUsername(registrationDTO.getUsername())){
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername().trim());
        user.setEmail(registrationDTO.getEmail().trim());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword().trim()));
        user.setFirstname(registrationDTO.getFirstname().trim());
        user.setLastname(registrationDTO.getLastname().trim());
        if (registrationDTO.getMiddlename()!=null && !registrationDTO.getMiddlename().isBlank()){
            user.setMiddlename(registrationDTO.getMiddlename().trim());
        } else {
            user.setMiddlename(null);
        }

        User newUser = userRepository.save(user);
        return userMapper.toDTO(newUser);
    }

    @Override
    public Optional<UserDTO> loginUser(LoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByUsername(loginDTO.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(loginDTO.getUsernameOrEmail()));

        if (userOpt.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword())) {
            return Optional.of(userMapper.toDTO(userOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getEmail().equals(userDTO.getEmail()) && existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (!user.getUsername().equals(userDTO.getUsername()) && existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setMiddlename(userDTO.getMiddlename());

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    /*
    public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
}


    public void updatePassword(Long userId, UpdatePasswordDTO dto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Old password does not match");
    }
    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(user);
}
*/
}
