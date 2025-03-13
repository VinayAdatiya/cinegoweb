package dao;

import common.Message;
import common.exception.DBException;
import model.Crew;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrewDAOImpl implements ICrewDAO{
    public int addCrew(Crew crew) throws DBException {
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

    public List<Crew> getAllCrew() throws DBException {
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