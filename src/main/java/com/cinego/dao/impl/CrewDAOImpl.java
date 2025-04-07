package com.cinego.dao.impl;

import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.config.DBConnection;
import com.cinego.dao.ICrewDAO;
import com.cinego.model.Crew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDAOImpl implements ICrewDAO {
    @Override
    public void addCrew(Crew crew) throws DBException {
        String query = "INSERT INTO crew (crew_name) VALUES (?)";
        PreparedStatement preparedStatement = null;
        Connection connection;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, crew.getCrewName());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    public List<Crew> getAllCrew() throws DBException {
        String query = "SELECT * FROM crew";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Crew> crewList = new ArrayList<>();
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Crew crew = new Crew(resultSet.getInt("crew_id"), resultSet.getString("crew_name"));
                crewList.add(crew);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
        return crewList;
    }

    @Override
    public Crew getCrewById(int crewId, Connection connection) throws DBException {
        Crew crew;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT crew_id, crew_name FROM crew WHERE crew_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, crewId);
            resultSet = preparedStatement.executeQuery();
            crew = new Crew();
            if (resultSet.next()) {
                crew.setCrewId(resultSet.getInt("crew_id"));
                crew.setCrewName(resultSet.getString("crew_name"));
            }
            return crew;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }
}