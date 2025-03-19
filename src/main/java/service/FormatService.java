package service;

import common.exception.DBException;
import dao.impl.FormatDAOImpl;
import dao.IFormatDAO;
import model.Format;
import java.util.List;

public class FormatService {
    private final IFormatDAO formatDAO = new FormatDAOImpl();

    public List<Format> getAllFormats() throws DBException {
        return formatDAO.getAllFormats();
    }
}
