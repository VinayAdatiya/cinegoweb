package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnection;
import dao.ISeatDAO;
import model.Seat;
import model.SeatCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Seat> getSeatsByScreenId(int screenId) throws DBException{
        String query = "SELECT * FROM seats WHERE screen_id = ?";
        List<Seat> seats = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screenId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setRowNum(resultSet.getInt("row_num"));
                seat.setColNum(resultSet.getInt("col_num"));
                SeatCategory seatCategory = new SeatCategory();
                seatCategory.setSeatCategoryId(resultSet.getInt("seat_category_id"));
                seat.setSeatCategory(seatCategory);
                seats.add(seat);
            }
            return seats;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }
}
