package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.Show;
import model.ShowSeat;

import java.sql.Connection;
import java.util.List;

public interface IShowSeatDAO {
    void addShowSeat(ShowSeat showSeat, Connection connection) throws DBException;

    List<ShowSeat> getSeatsByShowId(int showId) throws DBException;

    void resetShowSeatsQuery(List<Integer> seatIds, Connection connection) throws DBException;

    ShowSeat getShowSeatById(int showId, int seatId) throws ApplicationException;

    void confirmShowSeats(int showId, List<ShowSeat> showSeats, Connection connection) throws DBException;

    void updateShowSeats(List<ShowSeat> showSeats, Connection connection) throws DBException;
}
