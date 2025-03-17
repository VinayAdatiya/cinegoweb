package service;

import common.Role;
import common.exception.DBException;
import dao.IMovieDAO;
import dao.MovieDAOImpl;
import model.Movie;

import java.sql.SQLException;

public class MovieService {
    private final IMovieDAO movieDAO = new MovieDAOImpl();
    public int addMovie(Movie movie) throws DBException, SQLException {
        movie.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        return movieDAO.addMovie(movie);
    }
}
