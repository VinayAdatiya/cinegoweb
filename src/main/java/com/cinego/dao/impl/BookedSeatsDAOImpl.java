package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.dao.IBookedSeatsDAO;
import com.cinego.model.ShowSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookedSeatsDAOImpl implements IBookedSeatsDAO {

    @Override
    public void addBookedSeats(int generatedBookingId, List<ShowSeat> showSeats, Connection connection) throws DBException {
        String bookedSeatsQuery = "INSERT INTO booked_seats (booking_id, seat_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(bookedSeatsQuery);
            for (ShowSeat showSeat : showSeats) {
                preparedStatement.setInt(1, generatedBookingId);
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
    public List<ShowSeat> getBookedSeatsByBookingId(int bookingId) throws DBException {
        String query = "SELECT * FROM booked_seats WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ShowSeat> showSeats = new ArrayList<>();
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setSeatId(resultSet.getInt("seat_id"));
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
    public void resetBookedSeats(int bookingId, Connection connection) throws DBException {
        String resetBookedSeatQuery = "DELETE FROM booked_seats WHERE booking_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(resetBookedSeatQuery);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}
