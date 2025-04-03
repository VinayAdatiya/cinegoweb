package dao;

import common.exception.DBException;
import model.ShowSeat;

import java.sql.Connection;
import java.util.List;

public interface IBookedSeatsDAO {
    void addBookedSeats(int generatedBookingId, List<ShowSeat> showSeats, Connection connection) throws DBException;

    List<ShowSeat> getBookedSeatsByBookingId(int bookingId) throws DBException;

    void resetBookedSeats(int bookingId, Connection connection) throws DBException;
}
