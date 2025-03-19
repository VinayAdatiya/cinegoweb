package service;

import common.Role;
import common.exception.DBException;
import dao.IMovieDAO;
import dao.impl.MovieDAOImpl;
import model.Movie;

public class MovieService {
    private final IMovieDAO movieDAO = new MovieDAOImpl();

    public void addMovie(Movie movie) throws DBException {
        movie.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        movieDAO.addMovie(movie);
    }
}
