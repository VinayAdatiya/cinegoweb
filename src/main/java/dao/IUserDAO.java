package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.User;

import java.sql.SQLException;

public interface IUserDAO {
    void registerUser(User user) throws SQLException, DBException;
    User authenticateUser(String email, String password) throws DBException, ApplicationException;
    boolean emailExists(String email) throws DBException;
    boolean usernameExists(String username) throws DBException;
}
