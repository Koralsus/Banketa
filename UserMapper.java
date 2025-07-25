package com.banketa.banketa.Mapper;

import com.banketa.banketa.DTO.UserDTO;
import com.banketa.banketa.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}
