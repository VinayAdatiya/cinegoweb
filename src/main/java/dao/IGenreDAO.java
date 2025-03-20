package dao;

import common.exception.DBException;
import model.Genre;

import java.sql.Connection;
import java.util.List;

public interface IGenreDAO {
    List<Genre> getAllGenres() throws DBException;

    List<Genre> getGenresByMovieId(int movieId, Connection connection) throws DBException;
}
