package dao;

import common.exception.DBException;
import model.Genre;

import java.util.List;

public interface IGenreDAO {
    List<Genre> getAllGenres() throws DBException ;
}
