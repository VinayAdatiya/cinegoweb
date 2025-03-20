package dao;

import common.exception.DBException;
import model.Language;

import java.sql.Connection;
import java.util.List;

public interface ILanguageDAO {
    List<Language> getAllLanguages() throws DBException;

    List<Language> getLanguagesByMovieId(int movieId, Connection connection) throws DBException;
}
