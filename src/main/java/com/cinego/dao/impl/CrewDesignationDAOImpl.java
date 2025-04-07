package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.model.CrewDesignation;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ICrewDesignationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDesignationDAOImpl implements ICrewDesignationDAO {
    @Override
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

    @Override
    public CrewDesignation getDesignationById(int designationId, Connection connection) throws DBException {
        CrewDesignation designation;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT designation_id, designation_title FROM crew_designation WHERE designation_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, designationId);
            resultSet = preparedStatement.executeQuery();
            designation = new CrewDesignation();
            if (resultSet.next()) {
                designation.setDesignationId(resultSet.getInt("designation_id"));
                designation.setDesignationName(resultSet.getString("designation_title"));
            }
            return designation;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }
}