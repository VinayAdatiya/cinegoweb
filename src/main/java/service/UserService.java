package service;

import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IUserDAO;
import dao.UserDAOImpl;
import dto.user.UserResponseDTO;
import dto.user.UserSignUpDTO;
import mapper.IUserMapper;
import model.User;
import org.mapstruct.factory.Mappers;

import java.sql.SQLException;

public class UserService {
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserMapper userMapper = Mappers.getMapper(IUserMapper.class);

    public void registerUser(UserSignUpDTO userSignUpDTO) throws DBException, SQLException {
        userSignUpDTO.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        User user = userMapper.usersignupdtoToUser(userSignUpDTO);
        userDAO.registerUser(user);
    }

    public User authenticateUser(String email, String password) throws DBException, ApplicationException {
        return userDAO.authenticateUser(email, password);
    }

    public boolean emailExists(String email) throws DBException {
        return userDAO.emailExists(email);
    }

    public boolean usernameExists(String username) throws DBException {
        return userDAO.usernameExists(username);
    }

    public UserResponseDTO getUserById(int userId) {
        IUserDAO userDao = new UserDAOImpl();
        User user = userDao.getUserById(userId);
        return userMapper.userToUserResponseDTO(user);
    }
}