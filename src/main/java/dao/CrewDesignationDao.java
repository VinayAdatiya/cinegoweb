package dao;

import entity.CrewDesignation;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDesignationDao {
    public static List<CrewDesignation> getAllCrewDesignation() {
        List<CrewDesignation> designations = new ArrayList<>();
        String query = "SELECT designation_id , designation_name FROM formats";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();;
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                designations.add(new CrewDesignation(rs.getInt("designation_id"), rs.getString("designation_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs,preparedStatement,connection);
        }
        return designations;
    }
}
