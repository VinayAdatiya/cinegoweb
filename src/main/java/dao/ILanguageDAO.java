package dao;

import common.exception.DBException;
import model.Language;

import java.util.List;

public interface ILanguageDAO {
    List<Language> getAllLanguages() throws DBException;
}
