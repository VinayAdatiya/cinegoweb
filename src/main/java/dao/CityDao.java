package dao;

import entity.City;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDao {
    public static List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT city_id , city_name FROM city");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cities.add(new City(rs.getInt("city_id"), rs.getString("city_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs,preparedStatement,connection);
        }
        return cities;
    }
}
