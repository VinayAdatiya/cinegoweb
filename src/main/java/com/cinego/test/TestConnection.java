package com.cinego.test;

import com.cinego.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.INSTANCE.getConnection();
        System.out.println(connection);
    }
}
