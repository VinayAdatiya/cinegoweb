package dao;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import model.Theater;
import model.User;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TheaterDAOImpl implements ITheaterDAO{
    IUserDAO userDAO = new UserDAOImpl();
    IAddressDAO addressDAO = new AddressDAOImpl();
    public void addTheater(Theater theater) throws SQLException {
        String query = "INSERT INTO theater (theater_admin, theater_name, theater_rating, address_id, created_by) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            // Fetch Theater Admin
            User theaterAdmin = userDAO.authenticateUser(theater.getTheaterAdmin().getEmail(),theater.getTheaterAdmin().getPassword());
            if (theaterAdmin.getRole().getRoleId() != 2) {
                throw new ApplicationException(Message.Error.THEATER_ADMIN_NOT_FOUND);
            }
            theater.setTheaterAdmin(theaterAdmin);
            // Insert theaterAddress
            int addressId = addressDAO.insertAddress(theater.getTheaterAddress(),connection);
            theater.getTheaterAddress().setAddressId(addressId);
            // Add theater
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // Get generated keys
            preparedStatement.setInt(1, theater.getTheaterAdmin().getUserId());
            preparedStatement.setString(2, theater.getTheaterName());
            preparedStatement.setObject(3, theater.getTheaterRating());
            preparedStatement.setInt(4, theater.getTheaterAddress().getAddressId());
            preparedStatement.setInt(5, theater.getCreatedBy());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedTheaterId = generatedKeys.getInt(1);
                    theater.setTheaterId(generatedTheaterId);
                }
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new DBException(Message.Error.INTERNAL_ERROR , e);
        } finally {
            DBConnection.closeResources(generatedKeys, preparedStatement, connection);
        }
    }
}