package dao;

import common.exception.DBException;
import model.Movie;

import java.sql.SQLException;

public interface IMovieDAO {
    int addMovie(Movie movie) throws DBException, SQLException;
}
