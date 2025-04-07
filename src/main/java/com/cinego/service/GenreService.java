package com.cinego.service;

import com.cinego.dao.impl.GenreDAOImpl;
import com.cinego.model.Genre;
import com.cinego.common.exception.DBException;
import com.cinego.dao.IGenreDAO;

import java.util.List;

public class GenreService {
    private final IGenreDAO genreDAO = new GenreDAOImpl();

    public List<Genre> getAllGenres() throws DBException {
        return genreDAO.getAllGenres();
    }
}
