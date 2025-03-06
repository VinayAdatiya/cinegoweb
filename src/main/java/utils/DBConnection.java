package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DBConnection {
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static String dbDriver;

    public static void setDBConfig(String url , String username , String password , String driver ){
        dbUrl = url;
        dbUsername = username;
        dbPassword = password;
        dbDriver = driver;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            // Load the JDBC driver
            Class.forName(dbDriver);
            // Establish the connection
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeResources(ResultSet rs, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (rs != null) rs.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
