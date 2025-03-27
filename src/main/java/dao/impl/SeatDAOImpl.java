package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnection;
import dao.ISeatDAO;
import model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SeatDAOImpl implements ISeatDAO {
    public void addSeat(Seat seat,int screenId, Connection connection) throws DBException {
        String query = "INSERT INTO seats (screen_id , row_num , col_num ,seat_category_id) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenId);
            preparedStatement.setInt(2, seat.getRowNum());
            preparedStatement.setInt(3, seat.getColNum());
            preparedStatement.setInt(4, seat.getSeatCategory().getSeatCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}
