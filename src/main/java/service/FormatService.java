package service;

import dao.FormatDAOImpl;
import dao.IFormatDAO;
import model.Format;

import java.util.List;

public class FormatService {
    private final IFormatDAO formatDAO = new FormatDAOImpl();
    public List<Format> getAllFormats(){
        return formatDAO.getAllFormats();
    }
}
