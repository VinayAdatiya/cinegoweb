package utils;

import java.sql.*;
import common.Message;
import common.exception.DBException;

public class DatabaseUtil {
    public static boolean checkRecordExists(String tableName, String columnName, String value) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            rs = preparedStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR , e);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
    }
}