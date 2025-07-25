package com.banketa.banketa.Mapper;

import com.banketa.banketa.DTO.TransactionDTO;
import com.banketa.banketa.Entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO dto);
}
