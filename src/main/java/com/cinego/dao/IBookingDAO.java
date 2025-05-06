package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.Booking;
import com.cinego.model.ShowSeat;

import java.util.List;

public interface IBookingDAO {

    Booking createBooking(Booking booking, List<ShowSeat> showSeats) throws DBException;

    Booking confirmBooking(Booking booking) throws DBException;

    Booking getBookingById(int bookingId) throws DBException;

    List<Booking> getBookingByUser(int userId) throws DBException;

    void resetExpiredSeats(int bookingId, int currentUserId) throws DBException;

    void cancelBooking(int bookingId, int currentUserId) throws DBException;
}