package dao;

import common.exception.DBException;
import model.Booking;
import model.ShowSeat;

import java.util.List;

public interface IBookingDAO {

    Booking createBooking(Booking booking, List<ShowSeat> showSeats) throws DBException;

    Booking confirmBooking(Booking booking) throws DBException;

    Booking getBookingById(int bookingId) throws DBException;

    void resetExpiredSeats(int bookingId, int currentUserId) throws DBException;
}