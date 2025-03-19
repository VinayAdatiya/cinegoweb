package dao;

import common.exception.DBException;
import model.Movie;

public interface IMovieDAO {
    void addMovie(Movie movie) throws DBException;
}
