package com.cinego.service;

import com.cinego.model.Format;
import com.cinego.common.exception.DBException;
import com.cinego.dao.impl.FormatDAOImpl;
import com.cinego.dao.IFormatDAO;

import java.util.List;

public class FormatService {
    private final IFormatDAO formatDAO = new FormatDAOImpl();

    public List<Format> getAllFormats() throws DBException {
        return formatDAO.getAllFormats();
    }
}
