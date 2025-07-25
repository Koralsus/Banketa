package com.banketa.banketa.Service;

import com.banketa.banketa.DTO.AccountDTO;
import com.banketa.banketa.Entity.Account;
import com.banketa.banketa.Entity.AccountStatus;
import com.banketa.banketa.Entity.AccountType;
import com.banketa.banketa.Entity.User;
import com.banketa.banketa.Mapper.AccountMapper;
import com.banketa.banketa.Repository.AccountRepository;
import com.banketa.banketa.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    @Override
    public Optional<AccountDTO> findByAccountNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDTO);
    }

    @Override
    public List<AccountDTO> findByCreation(LocalDateTime creation){
        return accountRepository.findByCreation(creation)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByCreationBefore(LocalDateTime creation){
        return accountRepository.findByCreationBefore(creation)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByCreationAfter(LocalDateTime creation){
        return accountRepository.findByCreationAfter(creation)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByCreationBetween(LocalDateTime start, LocalDateTime end){
        return accountRepository.findByCreationBetween(start, end)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByUserId(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return accountRepository.findByUser(user)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByAccountType(AccountType accountType){
        return accountRepository.findByAccountType(accountType)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByAccountStatus(AccountStatus accountStatus){
        return accountRepository.findByAccountStatus(accountStatus)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO saveAccount(AccountDTO accountDTO){
        Account account = accountMapper.toEntity(accountDTO);
        if (accountDTO.getUser() != null && accountDTO.getUser().getUserId() != null) {
            User user = userRepository.findById(accountDTO.getUser().getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            account.setUser(user);
        } else {
            throw new IllegalArgumentException("User ID must be provided for account creation");
        }
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }

    @Override
    public void deleteAccountById(Long id){
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDTO> findAll(){
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountDTO> findById(Long id){
        return accountRepository.findById(id)
                .map(accountMapper::toDTO);
    }
}
