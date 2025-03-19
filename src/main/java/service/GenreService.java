package service;

import common.exception.DBException;
import dao.impl.GenreDAOImpl;
import dao.IGenreDAO;
import model.Genre;

import java.util.List;

public class GenreService {
    private final IGenreDAO genreDAO = new GenreDAOImpl();

    public List<Genre> getAllGenres() throws DBException {
        return genreDAO.getAllGenres();
    }
}
