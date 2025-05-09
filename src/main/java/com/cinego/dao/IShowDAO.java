package com.cinego.dao;

import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.model.Show;

import java.util.List;

public interface IShowDAO {
    void addShow(Show show, int currentUserId) throws DBException;

    Show getShowById(int showId) throws DBException;

    List<Show> getShowByTheaterId(int theaterId) throws DBException;

    List<Show> getShowByMovieId(int movieId) throws DBException;

    List<Show> getAllShows() throws DBException;

    void updateShow(Show show, int currentUserId) throws DBException;

    void deleteShow(int showId) throws DBException;

    void checkShowTiming(int showId) throws ApplicationException;

}
