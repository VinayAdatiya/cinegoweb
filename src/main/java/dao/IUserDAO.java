package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.User;

public interface IUserDAO {
    void registerUser(User user) throws DBException;
    User authenticateUser(String email, String password) throws ApplicationException;
    User getUserById(int userId) throws DBException;
    boolean isEmailExist(String email) throws DBException;
    boolean isUsernameExist(String username) throws DBException;
}
