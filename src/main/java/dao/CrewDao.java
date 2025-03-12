package dao;

import common.Message;
import common.exception.DBException;
import entity.Crew;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDao {

    public static int addCrew(Crew crew) throws DBException {
        String query = "INSERT INTO crew (crew_name) VALUES (?)";
        PreparedStatement preparedStatement;
        Connection connection;
        int generatedId = -1;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, crew.getCrewName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated crew ID.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return generatedId;
    }

    public static Crew getCrewById(int crewId) throws DBException {
        String query = "SELECT * FROM crew WHERE crew_id = ?";
        Crew crew = null;

        try (Connection connection = DBConnection.INSTANCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, crewId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    crew = new Crew(resultSet.getInt("crew_id"), resultSet.getString("crew_name"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return crew;
    }

    public static List<Crew> getAllCrew() throws DBException {
        String query = "SELECT * FROM crew";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        List<Crew> crewList = new ArrayList<>();
        try{
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Crew crew = new Crew(resultSet.getInt("crew_id"), resultSet.getString("crew_name"));
                crewList.add(crew);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
        return crewList;
    }
}