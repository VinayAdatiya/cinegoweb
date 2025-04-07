package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ICityDAO;
import com.cinego.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAOImpl implements ICityDAO {
    @Override
    public List<City> getAllCities() throws DBException {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city_id , city_name FROM city";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cities.add(new City(rs.getInt("city_id"), rs.getString("city_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
        return cities;
    }
}
