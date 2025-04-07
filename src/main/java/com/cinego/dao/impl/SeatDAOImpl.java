package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.dao.ISeatDAO;
import com.cinego.model.SeatCategory;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ISeatCategoryDAO;
import com.cinego.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOImpl implements ISeatDAO {

    private final ISeatCategoryDAO seatCategoryDAO = new SeatCategoryDAOImpl();

    @Override
    public void addSeat(Seat seat, int screenId, Connection connection) throws DBException {
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

    @Override
    public List<Seat> getSeatsByScreenId(int screenId) throws DBException {
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

    @Override
    public Seat getSeatById(int seatId) throws DBException {
        String query = "SELECT * FROM seats WHERE seat_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, seatId);
            resultSet = preparedStatement.executeQuery();
            Seat seat = new Seat();
            if (resultSet.next()) {
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setRowNum(resultSet.getInt("row_num"));
                seat.setColNum(resultSet.getInt("col_num"));
                SeatCategory seatCategory = seatCategoryDAO.getSeatCategoryById(resultSet.getInt("seat_category_id"));
                seat.setSeatCategory(seatCategory);
            }
            return seat;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void checkSeatType(int seatId) throws ApplicationException {
        Seat seat = getSeatById(seatId);
        if (seat.getSeatCategory().getSeatCategoryId() == 1) {
            throw new ApplicationException(Message.Error.SEAT_NOT_AVAILABLE);
        }
    }
}
