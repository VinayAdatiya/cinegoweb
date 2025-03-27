package dao;

import common.exception.DBException;
import model.Seat;
import java.sql.Connection;

public interface ISeatDAO {
    void addSeat(Seat seat, int screenId, Connection connection) throws DBException;
}
