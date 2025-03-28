package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnection;
import dao.ISeatCategoryDAO;
import model.SeatCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatCategoryDAOImpl implements ISeatCategoryDAO {

    @Override
    public List<SeatCategory> getAllSeatCategories() throws DBException {
        String query = "SELECT seat_category_id, seat_type FROM seat_category";
        List<SeatCategory> seatCategories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SeatCategory seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                seatCategory.setSeatType(resultSet.getString("seat_type"));
                seatCategories.add(seatCategory);
            }
            return seatCategories;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<SeatCategory> getSeatCategoriesByScreen(int screenId) throws DBException {
        String query = "SELECT DISTINCT sc.seat_category_id, sc.seat_type " +
                "FROM seat_category sc " +
                "JOIN seats s ON sc.seat_category_id = s.seat_category_id " +
                "WHERE s.screen_id = ?";
        List<SeatCategory> seatCategories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SeatCategory seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                seatCategory.setSeatType(resultSet.getString("seat_type"));
                seatCategories.add(seatCategory);
            }
            return seatCategories;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public SeatCategory getSeatCategoryById(int seatCategoryId) throws DBException {
        String query = "SELECT seat_category_id, seat_type FROM seat_category WHERE seat_category_id = ?";
        SeatCategory seatCategory = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, seatCategoryId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                seatCategory.setSeatType(resultSet.getString("seat_type"));
            }
            return seatCategory;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }
}
