package service;

import common.exception.DBException;
import dao.ILanguageDAO;
import dao.impl.LanguageDAOImpl;
import model.Language;

import java.util.List;

public class LanguageService {
    private final ILanguageDAO languageDAO = new LanguageDAOImpl();

    public List<Language> getAllLanguages() throws DBException {
        return languageDAO.getAllLanguages();
    }
}
