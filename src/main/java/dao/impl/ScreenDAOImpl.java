package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.*;
import config.DBConnection;
import model.Screen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAOImpl implements IScreenDAO {

    private final IScreenTypeDAO screenTypeDAO = new ScreenTypeDAOImpl();
    private final ITheaterDAO theaterDAO = new TheaterDAOImpl();

    @Override
    public void addScreen(Screen screen) throws DBException {
        String query = "INSERT INTO screen " +
                "(screen_title, total_seats, screen_type_id, layout, theater_id, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, screen.getScreenTitle());
            preparedStatement.setInt(2, screen.getTotalSeats());
            preparedStatement.setInt(3, screen.getScreenType().getScreenTypeId());
            preparedStatement.setString(4, screen.getLayout());
            preparedStatement.setInt(5, screen.getTheater().getTheaterId());
            preparedStatement.setInt(6, screen.getCreatedBy());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public Screen getScreenById(int screenId) throws DBException {
        String query = "SELECT * FROM screen WHERE screen_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet screenResult = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenId);
            screenResult = preparedStatement.executeQuery();
            Screen screen = null;
            if (screenResult.next()) {
                screen = new Screen();
                screen.setScreenId(screenResult.getInt("screen_id"));
                screen.setScreenTitle(screenResult.getString("screen_title"));
                screen.setTotalSeats(screenResult.getInt("total_seats"));
                screen.setLayout(screenResult.getString("layout"));
                screen.setScreenType(screenTypeDAO.getScreenTypeById(screenResult.getInt("screen_type_id")));
                screen.setTheater(theaterDAO.getTheaterById(screenResult.getInt("theater_id")));
            }
            return screen;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(screenResult, preparedStatement, connection);
        }

    }

    @Override
    public List<Screen> getAllScreens() throws DBException {
        String query = "SELECT * FROM screen";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet screenResult = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            screenResult = preparedStatement.executeQuery();
            List<Screen> screens = new ArrayList<>();
            while (screenResult.next()) {
                Screen screen = new Screen();
                screen.setScreenId(screenResult.getInt("screen_id"));
                screen.setScreenTitle(screenResult.getString("screen_title"));
                screen.setTotalSeats(screenResult.getInt("total_seats"));
                screen.setLayout(screenResult.getString("layout"));
                screen.setScreenType(screenTypeDAO.getScreenTypeById(screenResult.getInt("screen_type_id")));
                screen.setTheater(theaterDAO.getTheaterById(screenResult.getInt("theater_id")));
                screens.add(screen);
            }
            return screens;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(screenResult, preparedStatement, connection);
        }
    }

    @Override
    public void updateScreen(Screen screen) throws DBException {
        String query = "UPDATE screen " +
                "SET screen_title = ?, total_seats = ?, screen_type_id = ?, layout = ?" +
                ", updated_by = ? " +
                "WHERE screen_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, screen.getScreenTitle());
            preparedStatement.setInt(2, screen.getTotalSeats());
            preparedStatement.setInt(3, screen.getScreenType().getScreenTypeId());
            preparedStatement.setString(4, screen.getLayout());
            preparedStatement.setInt(5, screen.getUpdatedBy());
            preparedStatement.setInt(6, screen.getScreenId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    @Override
    public void deleteScreen(int screenId) throws DBException {
        String query = "DELETE FROM screen WHERE screen_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }
}
