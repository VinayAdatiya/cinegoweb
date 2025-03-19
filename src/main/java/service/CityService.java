package service;

import common.exception.DBException;
import dao.impl.CityDAOImpl;
import dao.ICityDAO;
import model.City;

import java.util.List;

public class CityService {
    private final ICityDAO cityDAO = new CityDAOImpl();

    public List<City> getAllCities() throws DBException {
        return cityDAO.getAllCities();
    }
}
