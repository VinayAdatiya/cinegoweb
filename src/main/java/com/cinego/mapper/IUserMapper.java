package com.cinego.mapper;

import com.cinego.dto.user.UserResponseDTO;
import com.cinego.dto.user.UserSignUpDTO;
import com.cinego.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {
    UserResponseDTO toUserDTO(User user); //toDTO
    User toUserModel(UserSignUpDTO userDTO); //toModel
}