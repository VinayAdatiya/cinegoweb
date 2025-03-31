package dao;

import common.exception.DBException;
import model.Show;
import model.ShowSeat;

import java.sql.Connection;
import java.util.List;

public interface IShowSeatDAO {
    void addShowSeat(ShowSeat showSeat, Connection connection) throws DBException;

    List<ShowSeat> getSeatsByShowId(int showId) throws DBException;

    void resetShowSeatsQuery(Connection connection);

    ShowSeat getShowSeatById(int showId, int seatId);
}
