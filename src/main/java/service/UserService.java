package service;

import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IUserDAO;
import dao.impl.UserDAOImpl;
import dto.user.UserResponseDTO;
import dto.user.UserSignUpDTO;
import mapper.IUserMapper;
import model.Movie;
import model.User;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserMapper userMapper = Mappers.getMapper(IUserMapper.class);

    public void registerUser(UserSignUpDTO userSignUpDTO) throws DBException {
        userSignUpDTO.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        User user = userMapper.toUserModel(userSignUpDTO);
        userDAO.registerUser(user);
    }

    public List<UserResponseDTO> getAllUsers() throws DBException {
        List<User> users = userDAO.getAllUser();
        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO authenticateUser(String email, String password) throws ApplicationException {
        User user = userDAO.authenticateUser(email, password);
        return userMapper.toUserDTO(user);
    }

    public boolean isEmailExist(String email) throws DBException {
        return userDAO.isEmailExist(email);
    }

    public boolean usernameExists(String username) throws DBException {
        return userDAO.isUsernameExist(username);
    }

    public UserResponseDTO getUserById(int userId) {
        IUserDAO userDao = new UserDAOImpl();
        User user = userDao.getUserById(userId);
        return userMapper.toUserDTO(user);
    }
}