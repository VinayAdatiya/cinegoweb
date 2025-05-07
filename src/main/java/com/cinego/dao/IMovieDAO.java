package com.cinego.dao;

import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.model.*;

import java.util.List;

public interface IMovieDAO {
    void addMovie(Movie movie) throws DBException;

    Movie getMovieById(int movieId) throws DBException;

    List<Movie> getAllMovies(int page, int pageSize, String sortBy, String sortOrder, List<String> languages, List<String> formats, List<String> genres) throws DBException;

    void updateMovie(Movie movie) throws DBException;

    void deleteMovie(int movieId) throws ApplicationException;
}
