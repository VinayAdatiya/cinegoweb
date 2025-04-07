package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.Genre;

import java.sql.Connection;
import java.util.List;

public interface IGenreDAO {
    List<Genre> getAllGenres() throws DBException;

    List<Genre> getGenresByMovieId(int movieId, Connection connection) throws DBException;
}
