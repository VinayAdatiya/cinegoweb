package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.ICrewDesignation;
import model.CrewDesignation;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDesignationDAOImpl implements ICrewDesignation {
    public List<CrewDesignation> getAllCrewDesignation() throws DBException {
        String query = "SELECT * FROM crew_designation";
        List<CrewDesignation> designationList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CrewDesignation designation = new CrewDesignation(resultSet.getInt("designation_id"), resultSet.getString("designation_title"));
                designationList.add(designation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
        return designationList;
    }
}