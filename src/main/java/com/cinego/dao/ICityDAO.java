package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.City;

import java.util.List;

public interface ICityDAO {
    List<City> getAllCities() throws DBException;
}
