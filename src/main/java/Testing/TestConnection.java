package Testing;

import utils.DBConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        System.out.println(connection);
    }
}
