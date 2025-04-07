package com.cinego.service;

import com.cinego.dao.impl.LanguageDAOImpl;
import com.cinego.model.Language;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ILanguageDAO;

import java.util.List;

public class LanguageService {
    private final ILanguageDAO languageDAO = new LanguageDAOImpl();

    public List<Language> getAllLanguages() throws DBException {
        return languageDAO.getAllLanguages();
    }
}
