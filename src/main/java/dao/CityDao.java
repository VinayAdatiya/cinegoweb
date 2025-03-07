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
        String query = "SELECT city_id , city_name FROM city";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();;
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cities.add(new City(rs.getInt("city_id"), rs.getString("city_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs,preparedStatement,connection);
        }
        return cities;
    }
}
