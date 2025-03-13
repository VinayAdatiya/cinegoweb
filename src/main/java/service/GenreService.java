package service;

import dao.GenreDAOImpl;
import dao.IGenreDAO;
import model.Genre;

import java.util.List;

public class GenreService {
    private final IGenreDAO genreDAO = new GenreDAOImpl();
    public List<Genre> getAllGenres(){
        return genreDAO.getAllGenres();
    }
}
