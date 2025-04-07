package com.cinego.dao;

import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.model.ShowSeat;

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
