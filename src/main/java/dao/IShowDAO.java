package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.Show;

import java.util.List;

public interface IShowDAO {
    void addShow(Show show, int currentUserId) throws DBException;

    Show getShowById(int showId) throws DBException;

    List<Show> getAllShows() throws DBException;

    void updateShow(Show show, int currentUserId) throws DBException;

    void deleteShow(int showId) throws DBException;

    void checkShowTiming(int showId) throws ApplicationException;
}
