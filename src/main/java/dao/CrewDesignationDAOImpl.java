package dao;

import common.Message;
import common.exception.DBException;
import model.CrewDesignation;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDesignationDAOImpl implements ICrewDesignation {
    public List<CrewDesignation> getAllCrewDesignation() throws DBException {
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