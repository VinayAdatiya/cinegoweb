package com.cinego.dao.impl;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.config.DBConnection;
import com.cinego.dao.*;
import com.cinego.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShowDAOImpl implements IShowDAO {

    private final IShowPriceCategoryDAO showPriceCategoryDAO = new ShowPriceCategoryDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();
    private final IScreenDAO screenDAO = new ScreenDAOImpl();
    private final IMovieDAO movieDAO = new MovieDAOImpl();
    private final ISeatDAO seatDAO = new SeatDAOImpl();

    @Override
    public void addShow(Show show, int currentUserId) throws DBException {
        String query = "INSERT INTO shows (movie_id, screen_id, show_date, show_time, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, show.getMovie().getMovieId());
            preparedStatement.setInt(2, show.getScreen().getScreenId());
            preparedStatement.setDate(3, Date.valueOf(show.getShowDate()));
            preparedStatement.setTime(4, Time.valueOf(show.getShowTime()));
            preparedStatement.setInt(5, show.getCreatedBy());
            preparedStatement.setInt(6, show.getUpdatedBy());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int showId = resultSet.getInt(1);
                show.setShowId(showId);
                List<ShowPriceCategory> showPriceCategoryList = show.getShowPriceCategoryList();
                showPriceCategoryDAO.addShowPriceCategory(showId, showPriceCategoryList, connection);
                InitializeShowSeats(show, currentUserId, connection);
            }
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    private void InitializeShowSeats(Show show, int currentUserId, Connection connection) {
        int showId = show.getShowId();
        int screenId = show.getScreen().getScreenId();
        List<ShowPriceCategory> showPriceCategoryList = show.getShowPriceCategoryList();
        List<Seat> seats = seatDAO.getSeatsByScreenId(screenId);

        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShowId(showId);
            double seatPrice = getSeatPrice(showPriceCategoryList, seat.getSeatCategory().getSeatCategoryId());
            if (seatPrice == 0) {
                throw new ApplicationException(Message.Error.INVALID_SHOW_PRICE_CATEGORY);
            }
            showSeat.setSeatPrice(seatPrice);
            showSeat.setSeatId(seat.getSeatId());
            showSeat.setCreatedBy(currentUserId);
            showSeat.setUpdatedBy(currentUserId);
            showSeatDAO.addShowSeat(showSeat, connection);
        }
    }

    private double getSeatPrice(List<ShowPriceCategory> showPriceCategoryList, int seatCategoryId) {
        for (ShowPriceCategory spc : showPriceCategoryList) {
            if (spc.getSeatCategory().getSeatCategoryId() == seatCategoryId) {
                return spc.getBasePrice();
            }
        }
        return 0.0;
    }

    @Override
    public Show getShowById(int showId) throws DBException {
        String query = "SELECT * FROM shows WHERE show_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, showId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Show show = new Show();
                show.setShowId(resultSet.getInt("show_id"));
                int movieId = resultSet.getInt("movie_id");
                Movie movie = movieDAO.getMovieById(movieId);
                show.setMovie(movie);
                int screenId = resultSet.getInt("screen_id");
                Screen screen = screenDAO.getScreenById(screenId);
                show.setScreen(screen);
                List<ShowPriceCategory> showPriceCategoryList = showPriceCategoryDAO.getShowPriceCategoriesByShow(showId);
                show.setShowPriceCategoryList(showPriceCategoryList);
                show.setShowDate(resultSet.getDate("show_date").toLocalDate());
                show.setShowTime(resultSet.getTime("show_time").toLocalTime());
                show.setCreatedOn(resultSet.getTimestamp("created_on").toLocalDateTime());
                show.setCreatedBy(resultSet.getInt("created_by"));
                show.setUpdatedOn(resultSet.getTimestamp("updated_on").toLocalDateTime());
                show.setUpdatedBy(resultSet.getInt("updated_by"));
                return show;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<Show> getAllShows() throws DBException {
        String query = "SELECT * FROM shows;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Show> shows = new ArrayList<>();
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                int showId = resultSet.getInt("show_id");
                show.setShowId(showId);
                int movieId = resultSet.getInt("movie_id");
                Movie movie = movieDAO.getMovieById(movieId);
                show.setMovie(movie);
                int screenId = resultSet.getInt("screen_id");
                Screen screen = screenDAO.getScreenById(screenId);
                show.setScreen(screen);
                List<ShowPriceCategory> showPriceCategoryList = showPriceCategoryDAO.getShowPriceCategoriesByShow(showId);
                show.setShowPriceCategoryList(showPriceCategoryList);
                show.setShowDate(resultSet.getDate("show_date").toLocalDate());
                show.setShowTime(resultSet.getTime("show_time").toLocalTime());
                show.setCreatedOn(resultSet.getTimestamp("created_on").toLocalDateTime());
                show.setCreatedBy(resultSet.getInt("created_by"));
                show.setUpdatedOn(resultSet.getTimestamp("updated_on").toLocalDateTime());
                show.setUpdatedBy(resultSet.getInt("updated_by"));
                shows.add(show);
            }
            return shows;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void updateShow(Show show, int currentUserId) throws DBException {
        String query = "UPDATE shows SET movie_id = ?, screen_id = ?, show_date = ?, show_time = ?, updated_by = ? WHERE show_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, show.getMovie().getMovieId());
            preparedStatement.setInt(2, show.getScreen().getScreenId());
            preparedStatement.setDate(3, Date.valueOf(show.getShowDate()));
            preparedStatement.setTime(4, Time.valueOf(show.getShowTime()));
            preparedStatement.setInt(5, currentUserId);
            preparedStatement.setInt(6, show.getShowId());
            preparedStatement.executeUpdate();
            showPriceCategoryDAO.updateShowPriceCategory(show.getShowId(), show.getShowPriceCategoryList(), currentUserId, connection);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }


    @Override
    public void deleteShow(int showId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM show_seats WHERE show_id = ?");
            preparedStatement.setInt(1, showId);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM show_price_category WHERE show_id = ?");
            preparedStatement.setInt(1, showId);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM shows WHERE show_id = ?");
            preparedStatement.setInt(1, showId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                throw new DBException(Message.Error.INTERNAL_ERROR, error);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    public void checkShowTiming(int showId) throws ApplicationException {
        Show show = getShowById(showId);
        if (show == null) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }

        LocalDate showDate = show.getShowDate();
        LocalTime showTime = show.getShowTime();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (showDate.isBefore(currentDate)) {
            throw new ApplicationException(Message.Error.SHOW_ALREADY_ENDED);
        } else if (showDate.equals(currentDate) && showTime.isBefore(currentTime)) {
            throw new ApplicationException(Message.Error.SHOW_ALREADY_ENDED);
        }
    }
}
