package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.*;

import java.util.List;

public interface IMovieDAO {
    void addMovie(Movie movie) throws DBException;

    Movie getMovieById(int movieId) throws DBException;

    List<Movie> getAllMovies() throws DBException;

    void updateMovie(Movie movie) throws DBException;

    void deleteMovie(int movieId) throws DBException;
}
