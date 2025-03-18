package mapper;

import dto.user.UserResponseDTO;
import dto.user.UserSignUpDTO;
import model.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {
    UserResponseDTO userToUserResponseDTO(User user);
    User usersignupdtoToUser(UserSignUpDTO userSignUpDTO);
}