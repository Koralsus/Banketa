package com.banketa.banketa.Mapper;

import com.banketa.banketa.DTO.AccountDTO;
import com.banketa.banketa.Entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO dto);
}
