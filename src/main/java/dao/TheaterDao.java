package dao;

import entity.Theater;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TheaterDao {

    public static void addTheater(Theater theater) {
        String query = "INSERT INTO theater (theater_admin, theater_name, theater_rating, address_id, created_by) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // Get generated keys
            preparedStatement.setInt(1, theater.getTheaterAdmin().getUserId());
            preparedStatement.setString(2, theater.getTheaterName());
            preparedStatement.setObject(3, theater.getTheaterRating());
            preparedStatement.setInt(4, theater.getTheaterAddress().getAddressId());
            preparedStatement.setInt(5, 1);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedTheaterId = generatedKeys.getInt(1);
                    theater.setTheaterId(generatedTheaterId);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(generatedKeys, preparedStatement, connection);
        }
    }
}