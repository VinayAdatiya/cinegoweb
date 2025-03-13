package service;

import dao.CityDAOImpl;
import dao.ICityDAO;
import model.City;

import java.util.List;

public class CityService {
    private final ICityDAO cityDAO = new CityDAOImpl();
    public List<City> getAllCities(){
        return cityDAO.getAllCities();
    }
}
