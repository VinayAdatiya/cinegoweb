package service;

import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IUserDAO;
import dao.UserDAOImpl;
import model.User;

import java.sql.SQLException;

public class UserService {
    private final IUserDAO userDAO = new UserDAOImpl();

    public void registerUser(User user) throws DBException, SQLException {
        user.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
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
}