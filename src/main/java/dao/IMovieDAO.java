package dao;

import common.exception.DBException;
import model.Movie;

import java.util.List;

public interface IMovieDAO {
    void addMovie(Movie movie) throws DBException;

    Movie getMovieById(int movieId) throws DBException;

    List<Movie> getAllMovies() throws DBException;

    void updateMovie(Movie movie) throws DBException;

    void deleteMovie(int movieId) throws DBException;
}
