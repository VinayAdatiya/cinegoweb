package dao;

import common.exception.DBException;
import model.ShowSeat;

import java.sql.Connection;
import java.util.List;

public interface IBookedSeatsDAO {
    List<ShowSeat> getBookedSeatsByBookingId(int bookingId) throws DBException;

    void resetBookedSeats(int bookingId, Connection connection) throws DBException;

}
