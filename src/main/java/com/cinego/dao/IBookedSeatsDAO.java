package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.ShowSeat;

import java.sql.Connection;
import java.util.List;

public interface IBookedSeatsDAO {
    void addBookedSeats(int generatedBookingId, List<ShowSeat> showSeats, Connection connection) throws DBException;

    List<ShowSeat> getBookedSeatsByBookingId(int bookingId) throws DBException;

    void resetBookedSeats(int bookingId, Connection connection) throws DBException;
}
