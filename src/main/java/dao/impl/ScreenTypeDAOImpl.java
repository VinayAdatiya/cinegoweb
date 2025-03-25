package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnection;
import dao.IScreenTypeDAO;
import model.ScreenType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScreenTypeDAOImpl implements IScreenTypeDAO {
    @Override
    public ScreenType getScreenTypeById(int screenTypeId) throws DBException {
        String query = "SELECT * FROM screen_types WHERE screen_type_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenTypeId);
            resultSet = preparedStatement.executeQuery();
            ScreenType screenType = null;
            if (resultSet.next()) {
                screenType = new ScreenType();
                screenType.setScreenTypeId(resultSet.getInt("screen_type_id"));
                screenType.setScreenType(resultSet.getString("screen_type"));
                screenType.setScreenTypeDescription(resultSet.getString("screen_type_description"));
            }
            return screenType;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, null);
        }
    }
}
