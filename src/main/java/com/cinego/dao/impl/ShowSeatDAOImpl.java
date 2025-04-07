package com.cinego.dao.impl;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.config.DBConnection;
import com.cinego.dao.IShowSeatDAO;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.DateTimeUtil;
import com.cinego.model.SeatCategory;
import com.cinego.model.ShowSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowSeatDAOImpl implements IShowSeatDAO {
    @Override
    public void addShowSeat(ShowSeat showSeat, Connection connection) throws DBException {
        String query = "INSERT INTO show_seats (show_id, seat_id, seat_price, created_by, updated_by) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, showSeat.getShowId());
            preparedStatement.setInt(2, showSeat.getSeatId());
            preparedStatement.setDouble(3, showSeat.getSeatPrice());
            preparedStatement.setInt(4, showSeat.getCreatedBy());
            preparedStatement.setInt(5, showSeat.getUpdatedBy());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    @Override
    public void updateShowSeats(List<ShowSeat> showSeats, Connection connection) throws DBException {
        String updateShowSeatQuery = "UPDATE show_seats SET available = 0, hold_until = CURRENT_TIMESTAMP + INTERVAL 3 MINUTE " +
                "WHERE show_id = ? AND seat_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(updateShowSeatQuery);
            for (ShowSeat showSeat : showSeats) {
                preparedStatement.setInt(1, showSeat.getShowId());
                preparedStatement.setInt(2, showSeat.getSeatId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    @Override
    public List<ShowSeat> getSeatsByShowId(int showId) throws DBException {
        String query = "SELECT " +
                "   ss.*, " +
                "   s.row_num, " +
                "   s.col_num, " +
                "   s.seat_category_id " +
                "FROM " +
                "   show_seats ss " +
                "JOIN " +
                "   seats s ON ss.seat_id = s.seat_id " +
                "WHERE " +
                "   ss.show_id = ?";
        List<ShowSeat> showSeats = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, showId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setShowId(resultSet.getInt("show_id"));
                showSeat.setSeatId(resultSet.getInt("seat_id"));
                showSeat.setSeatPrice(resultSet.getDouble("seat_price"));
                showSeat.setBooked(resultSet.getBoolean("is_booked"));
                showSeat.setAvailable(resultSet.getBoolean("available"));
                showSeat.setCreatedOn(DateTimeUtil.toLocalDateTime(resultSet.getTimestamp("created_on")));
                showSeat.setCreatedBy(resultSet.getInt("created_by"));
                showSeat.setUpdatedOn(DateTimeUtil.toLocalDateTime(resultSet.getTimestamp("updated_on")));
                showSeat.setUpdatedBy(resultSet.getInt("updated_by"));

                showSeat.setRowNum(resultSet.getInt("row_num"));
                showSeat.setColNum(resultSet.getInt("col_num"));

                SeatCategory seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                showSeat.setSeatCategory(seatCategory);

                showSeats.add(showSeat);
            }
            return showSeats;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void resetShowSeatsQuery(List<Integer> seats, Connection connection) throws DBException {
        String resetShowSeatsQuery = "UPDATE show_seats SET available = 1, hold_until = NULL " +
                "WHERE available = 0 AND hold_until < CURRENT_TIMESTAMP AND seat_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(resetShowSeatsQuery);
            for (int seatId : seats) {
                preparedStatement.setInt(1, seatId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

    @Override
    public ShowSeat getShowSeatById(int showId, int seatId) throws ApplicationException {
        String getSeat = "SELECT * FROM show_seats WHERE show_id = ? AND seat_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(getSeat);
            preparedStatement.setInt(1, showId);
            preparedStatement.setInt(2, seatId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setShowId(resultSet.getInt("show_id"));
                showSeat.setSeatId(resultSet.getInt("seat_id"));
                showSeat.setSeatPrice(resultSet.getDouble("seat_price"));
                showSeat.setBooked(resultSet.getBoolean("is_booked"));
                showSeat.setAvailable(resultSet.getBoolean("available"));
                showSeat.setUpdatedOn(DateTimeUtil.toLocalDateTime(resultSet.getTimestamp("updated_on")));
                return showSeat;
            } else {
                throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void confirmShowSeats(int showId, List<ShowSeat> showSeats, Connection connection) throws DBException {
        String confirmShowSeatsQuery = "UPDATE show_seats " +
                "SET hold_until = NULL, available = 0, is_booked = 1 " +
                "WHERE show_id = ? AND seat_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(confirmShowSeatsQuery);
            for (ShowSeat showSeat : showSeats) {
                preparedStatement.setInt(1, showId);
                preparedStatement.setInt(2, showSeat.getSeatId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}
