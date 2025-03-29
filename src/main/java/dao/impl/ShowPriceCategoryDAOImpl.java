package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnection;
import dao.IShowPriceCategoryDAO;
import model.SeatCategory;
import model.ShowPriceCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowPriceCategoryDAOImpl implements IShowPriceCategoryDAO {
    @Override
    public void addShowPriceCategory(int showId, List<ShowPriceCategory> priceCategories, Connection connection) throws DBException {
        String query = "INSERT INTO show_price_category (show_id, seat_category_id, base_price) " +
                "VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (ShowPriceCategory priceCategory : priceCategories) {
                preparedStatement.setInt(1, showId);
                preparedStatement.setInt(2, priceCategory.getSeatCategory().getSeatCategoryId());
                preparedStatement.setDouble(3, priceCategory.getBasePrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    @Override
    public void updateShowPriceCategory(int showId, List<ShowPriceCategory> showPriceCategoryList, int currentUserId, Connection connection) throws DBException {
        try {
            deleteShowPriceCategories(showId, connection);
            addShowPriceCategory(showId, showPriceCategoryList, connection);
            updateShowSeatsPrices(showId, showPriceCategoryList, connection);
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        }
    }

    private void deleteShowPriceCategories(int showId, Connection connection) throws SQLException {
        String query = "DELETE FROM show_price_category WHERE show_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, showId);
            preparedStatement.executeUpdate();
        }
    }

    private void updateShowSeatsPrices(int showId, List<ShowPriceCategory> showPriceCategoryList, Connection connection) throws SQLException {
        String query = "UPDATE show_seats " +
                "SET seat_price = ? " +
                "WHERE show_id = ? AND seat_id IN (SELECT seat_id FROM seats WHERE seat_category_id = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (ShowPriceCategory showPriceCategory : showPriceCategoryList) {
                preparedStatement.setDouble(1, showPriceCategory.getBasePrice());
                preparedStatement.setInt(2, showId);
                preparedStatement.setInt(3, showPriceCategory.getSeatCategory().getSeatCategoryId());
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<ShowPriceCategory> getShowPriceCategoriesByShow(int showId) throws DBException {
        String query = "SELECT spc.seat_category_id, spc.base_price, sc.seat_type " +
                "FROM show_price_category spc " +
                "JOIN seat_category sc ON spc.seat_category_id = sc.seat_category_id " +
                "WHERE spc.show_id = ?";
        List<ShowPriceCategory> showPriceCategories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, showId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShowPriceCategory showPriceCategory = new ShowPriceCategory();
                SeatCategory seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                seatCategory.setSeatType(resultSet.getString("seat_type"));
                showPriceCategory.setSeatCategory(seatCategory);
                showPriceCategory.setBasePrice(resultSet.getDouble("base_price"));
                showPriceCategories.add(showPriceCategory);
            }
            return showPriceCategories;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }
}