package mapper;

import dto.user.UserResponseDTO;
import dto.user.UserSignUpDTO;
import model.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {
    UserResponseDTO toUserDTO(User user); //toDTO
    User toUserModel(UserSignUpDTO userDTO); //toModel
}