package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.Seat;

import java.sql.Connection;
import java.util.List;

public interface ISeatDAO {
    void addSeat(Seat seat, int screenId, Connection connection) throws DBException;

    List<Seat> getSeatsByScreenId(int screenId) throws DBException;

    Seat getSeatById(int seatId) throws DBException;

    void checkSeatType(int seatId) throws ApplicationException;
}
