package dao;

import common.exception.DBException;
import model.City;
import java.util.List;

public interface ICityDAO {
    List<City> getAllCities() throws DBException;
}
