package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.Language;

import java.sql.Connection;
import java.util.List;

public interface ILanguageDAO {
    List<Language> getAllLanguages() throws DBException;

    List<Language> getLanguagesByMovieId(int movieId, Connection connection) throws DBException;
}
