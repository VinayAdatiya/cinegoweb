package common.utils;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseUtil {

    public static <T> boolean checkRecordExists(String tableName, String columnName, T value) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            preparedStatement = connection.prepareStatement(query);

            if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            } else {
                throw new IllegalArgumentException("Unsupported Id type");
            }
            rs = preparedStatement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(rs, preparedStatement, connection);
        }
    }

    public static void validateIdsExist(String tableName, String columnName, List<Integer> ids) throws ApplicationException {
        for (Integer id : ids) {
            if (id == null || id <= 0) {
                throw new ApplicationException(Message.Error.ID_INVALID);
            }
            if (!checkRecordExists(tableName, columnName, id)) {
                throw new ApplicationException(Message.Error.INVALID_INPUT);
            }
        }
    }
}