package dao.impl;

import common.Message;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IAddressDAO;
import dao.IUserDAO;
import model.Address;
import model.City;
import model.User;
import config.DBConnection;
import common.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {
    IAddressDAO addressDAO = new AddressDAOImpl();

    public void registerUser(User user) throws DBException {
        String query = "INSERT INTO users (username , password , first_name , last_name  , email  , phone_number ,address_id ,created_by , role_id) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            // Insert address first to get addressId
            int addressId = addressDAO.insertAddress(user.getAddress(), connection);
            // Updating user with generated addressId
            user.getAddress().setAddressId(addressId);
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setInt(7, user.getAddress().getAddressId());
            preparedStatement.setInt(8, user.getCreatedBy());
            preparedStatement.setInt(9, user.getRole().getRoleId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    public User authenticateUser(String email, String password) throws ApplicationException{
        String query = "SELECT user_id , username , first_name , last_name  , email  , phone_number , address_id, role_id " +
                "FROM users " +
                "WHERE email = ? and password = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAddress(new Address(rs.getInt("address_id")));
                int roleId = rs.getInt("role_id");
                user.setRole(Role.getRole(roleId));
                return user;
            } else {
                throw new ApplicationException(Message.Error.INVALID_CREDENTIALS);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
    }

    public User getUserById(int userId) throws DBException {
        String query = " SELECT u.user_id, u.username, u.email, u.first_name, u.last_name, u.roleid" +
                " a.address_line1, a.address_line2, a.pincode, " +
                " c.city_id, c.city_name," +
                " r.role_id" +
                " FROM users u " +
                " LEFT JOIN address a ON u.address_id = a.address_id " +
                " LEFT JOIN city c ON a.city_id = c.city_id " +
                " WHERE u.user_id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setRole(Role.getRole(resultSet.getInt("roleid")));
                Address address = new Address();
                address.setAddressLine1(resultSet.getString("address_line"));
                address.setAddressLine2(resultSet.getString("address_line2"));
                address.setPincode(resultSet.getInt("pincode"));
                City city = new City();
                city.setCityName(resultSet.getString("city_name"));
                address.setCity(city);
                user.setAddress(address);
            } else {
                throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
        return user;
    }

    public boolean isEmailExist(String email) {
        return DatabaseUtil.checkRecordExists("users", "email", email);
    }

    public boolean isUsernameExist(String username) {
        return DatabaseUtil.checkRecordExists("users", "username", username);
    }
}