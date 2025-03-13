package service;

import dao.ILanguageDAO;
import dao.LanguageDAOImpl;
import model.Language;

import java.util.List;

public class LanguageService {
    private final ILanguageDAO languageDAO = new LanguageDAOImpl();
    public List<Language> getAllLanguages(){
        return languageDAO.getAllLanguages();
    }
}
