package com.cinego.dao;

import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.model.Seat;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.List;

public interface ISeatDAO {
    void addSeat(Seat seat, int screenId, Connection connection) throws DBException;

    List<Seat> getSeatsByScreenId(int screenId) throws DBException;

    Seat getSeatById(int seatId) throws DBException;

    void checkSeatType(int seatId) throws ApplicationException;

    void deleteSeatsByScreen(int screenId, Connection connection) throws DBException;
}
