package dao;

import common.Message;
import common.exception.DBException;
import entity.CrewDesignation;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDesignationDao {

    public static int addCrewDesignation(CrewDesignation designation) throws DBException {
        String query = "INSERT INTO crew_designation (designation_title) VALUES (?)";
        int generatedId = -1;

        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, designation.getDesignationName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated crew designation ID.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return generatedId;
    }

    public static CrewDesignation getCrewDesignationById(int designationId) throws DBException {
        String query = "SELECT * FROM crew_designation WHERE designation_id = ?";
        CrewDesignation designation = null;

        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, designationId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    designation = new CrewDesignation(resultSet.getInt("designation_id"), resultSet.getString("designation_title"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return designation;
    }

    public static List<CrewDesignation> getAllCrewDesignation() throws DBException {
        String query = "SELECT * FROM crew_designation";
        List<CrewDesignation> designationList = new ArrayList<>();

        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                CrewDesignation designation = new CrewDesignation(resultSet.getInt("designation_id"), resultSet.getString("designation_title"));
                designationList.add(designation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return designationList;
    }
}