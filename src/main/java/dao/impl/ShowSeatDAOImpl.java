package dao.impl;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DateTimeUtil;
import config.DBConnection;
import dao.IShowSeatDAO;
import model.ShowSeat;

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

    public List<ShowSeat> getSeatsByShowId(int showId) throws DBException {
        String query = "SELECT * FROM show_seats WHERE show_id = ?";
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
                showSeat.setSeatId(resultSet.getInt("seat_id"));
                showSeat.setSeatPrice(resultSet.getDouble("seat_price"));
                showSeat.setBooked(resultSet.getBoolean("is_booked"));
                showSeat.setAvailable(resultSet.getBoolean("available"));
                showSeat.setUpdatedOn(DateTimeUtil.toLocalDateTime(resultSet.getTimestamp("updated_on")));
                showSeats.add(showSeat);
            }
            return showSeats;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    public void resetShowSeatsQuery(List<Integer> seats, Connection connection) {
        String resetShowSeatsQuery = "UPDATE show_seats SET available = 1, hold_until = NULL " +
                "WHERE available = 0 AND hold_until < CURRENT_TIMESTAMP AND seat_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            for (int seatId : seats) {
                preparedStatement = connection.prepareStatement(resetShowSeatsQuery);
                preparedStatement.setInt(1, seatId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }

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
}
