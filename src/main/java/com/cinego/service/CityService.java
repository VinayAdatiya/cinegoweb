package com.cinego.service;

import com.cinego.dao.impl.CityDAOImpl;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ICityDAO;
import com.cinego.model.City;

import java.util.List;

public class CityService {
    private final ICityDAO cityDAO = new CityDAOImpl();

    public List<City> getAllCities() throws DBException {
        return cityDAO.getAllCities();
    }
}
