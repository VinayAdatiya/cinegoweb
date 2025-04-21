package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.model.ScreenType;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.dao.IScreenTypeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScreenTypeDAOImpl implements IScreenTypeDAO {

    @Override
    public List<ScreenType> getAllScreenType() throws DBException {
        String query = "SELECT * FROM screen_types";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<ScreenType> screenTypeList = new ArrayList<>();
            while (resultSet.next()) {
                ScreenType screenType = new ScreenType();
                screenType.setScreenTypeId(resultSet.getInt("screen_type_id"));
                screenType.setScreenType(resultSet.getString("screen_type"));
                screenType.setScreenTypeDescription(resultSet.getString("screen_type_description"));
                screenTypeList.add(screenType);
            }
            return screenTypeList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, null);
        }
    }

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
