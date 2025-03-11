package dao;

import entity.City;
import entity.Language;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDao {
    public static List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>();
        String query = "SELECT language_id , language_name FROM languages";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();;
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                languages.add(new Language(rs.getInt("language_id"), rs.getString("language_name")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs,preparedStatement,connection);
        }
        return languages;
    }
}
