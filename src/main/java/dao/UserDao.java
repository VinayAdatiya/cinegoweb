package dao;

import common.Message;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import entity.Address;
import entity.User;
import utils.DBConnection;
import utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static void registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (username , password , first_name , last_name  , email  , phone_number ,address_id ,created_by , role_id) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            // Insert address first to get addressId
            int addressId = AddressDao.insertAddress(user.getAddress(), connection);
            // Updating user with generated addressId
            user.getAddress().setAddressId(addressId);
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setInt(7, user.getAddress().getAddressId());
            preparedStatement.setInt(8, 1); // Default Created By 1 (Super Admin)
            preparedStatement.setInt(9, user.getRole().getRoleId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new DBException(Message.Error.INTERNAL_ERROR,e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    public static User authenticateUser(String email, String password) {
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
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAddress(new Address(rs.getInt("address_id")));
                int roleId = rs.getInt("role_id");
                user.setRole(Role.getRole(roleId));
                return user;
            }
            else{
                throw new ApplicationException(Message.Error.INVALID_CREDENTIALS);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
    }

    public static boolean emailExists(String email) {
        return DatabaseUtil.checkRecordExists("users" , "email", email);
    }

    public static boolean usernameExists(String username) {
        return DatabaseUtil.checkRecordExists("users" , "username", username);
    }
}