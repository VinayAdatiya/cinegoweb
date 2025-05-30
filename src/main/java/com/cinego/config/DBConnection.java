package com.cinego.config;

import java.sql.*;

public class DBConnection {

    public static DBConnection INSTANCE = null;

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String dbDriver;

    private DBConnection(String url, String username, String password, String driver) throws SQLException, ClassNotFoundException {
        dbUrl = url;
        dbUsername = username;
        dbPassword = password;
        dbDriver = driver;
    }

    public static DBConnection getInstance(String url, String username, String password, String driver) throws SQLException, ClassNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new DBConnection(url,username,password,driver);
        }
        return INSTANCE;
    }

    public void validateConnection() throws SQLException, ClassNotFoundException {
        getConnection();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver
        Class.forName(this.dbDriver);
        // Establish the connection
        return DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
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
