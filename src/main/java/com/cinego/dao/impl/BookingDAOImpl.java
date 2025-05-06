package com.cinego.dao.impl;

import com.cinego.config.DBConnection;
import com.cinego.common.Message;
import com.cinego.common.enums.BookingStatus;
import com.cinego.common.exception.DBException;
import com.cinego.dao.*;
import com.cinego.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDAOImpl implements IBookingDAO {

    private final IBookedSeatsDAO bookedSeatsDAO = new BookedSeatsDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();
    private final ISeatDAO seatDAO = new SeatDAOImpl();

    @Override
    public Booking createBooking(Booking booking, List<ShowSeat> showSeats) throws DBException {
        String bookingQuery = "INSERT INTO bookings " +
                "(show_id, user_id, grand_total, number_of_seats, booking_status, payment_method_id, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement bookingPreparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            // 1. Create Booking
            bookingPreparedStatement = connection.prepareStatement(bookingQuery, Statement.RETURN_GENERATED_KEYS);
            bookingPreparedStatement.setInt(1, booking.getShow().getShowId());
            bookingPreparedStatement.setInt(2, booking.getUser().getUserId());
            bookingPreparedStatement.setDouble(3, booking.getGrandTotal());
            bookingPreparedStatement.setInt(4, booking.getNumberOfSeats());
            bookingPreparedStatement.setString(5, booking.getBookingStatus().getStatus());
            bookingPreparedStatement.setInt(6, booking.getPaymentMethod().getPaymentMethodId());
            bookingPreparedStatement.setInt(7, booking.getCreatedBy());
            bookingPreparedStatement.setInt(8, booking.getUpdatedBy());
            bookingPreparedStatement.executeUpdate();
            generatedKeys = bookingPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedBookingId = generatedKeys.getInt(1);
                booking.setBookingId(generatedBookingId);
            }
            // 2. Reserve Seats
            // Insert into booked_seats
            bookedSeatsDAO.addBookedSeats(booking.getBookingId(), showSeats, connection);
            // Update into show_seats
            showSeatDAO.updateShowSeats(showSeats, connection);
            connection.commit();
            booking = getBookingById(booking.getBookingId());
            return booking;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(generatedKeys, bookingPreparedStatement, connection);

        }
    }

    @Override
    public Booking confirmBooking(Booking booking) throws DBException {
        String updateBookingQuery = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            int bookingId = booking.getBookingId();
            int showId = booking.getShow().getShowId();
            // 1. Update Booking Status
            preparedStatement = connection.prepareStatement(updateBookingQuery);
            preparedStatement.setString(1, booking.getBookingStatus().getStatus());
            preparedStatement.setInt(2, bookingId);
            preparedStatement.executeUpdate();
            // 2. Clear hold_until for booked seats
            List<ShowSeat> showSeats = bookedSeatsDAO.getBookedSeatsByBookingId(bookingId);
            showSeatDAO.confirmShowSeats(showId, showSeats, connection);
            connection.commit();
            booking = getBookingById(booking.getBookingId());
            return booking;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) throws DBException {
        String query = "SELECT b.*, " +
                "   s.show_date, s.show_time, " +
                "   m.movie_title, m.movie_duration, " +
                "   sc.screen_id, sc.screen_title, " +
                "   st.screen_type, " +
                "   th.theater_id, th.theater_name, " +
                "   u.user_id, u.username, " +
                "   pm.payment_method " +
                "FROM bookings b " +
                "JOIN shows s ON b.show_id = s.show_id " +
                "JOIN movie m ON s.movie_id = m.movie_id " +
                "JOIN screen sc ON s.screen_id = sc.screen_id " +
                "JOIN screen_types st ON sc.screen_type_id = st.screen_type_id " +
                "JOIN theater th ON sc.theater_id = th.theater_id " +
                "JOIN users u ON b.user_id = u.user_id " +
                "JOIN payment_methods pm ON b.payment_method_id = pm.payment_method_id " +
                "WHERE b.booking_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Booking booking = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                Show show = new Show();
                show.setShowId(resultSet.getInt("show_id"));
                show.setShowDate(resultSet.getDate("show_date").toLocalDate());
                show.setShowTime(resultSet.getTime("show_time").toLocalTime());
                Movie movie = new Movie();
                movie.setMovieTitle(resultSet.getString("movie_title"));
                movie.setMovieDuration(resultSet.getTime("movie_duration").toLocalTime());
                show.setMovie(movie);
                Screen screen = new Screen();
                screen.setScreenId(resultSet.getInt("screen_id"));
                screen.setScreenTitle(resultSet.getString("screen_title"));
                ScreenType screenType = new ScreenType();
                screenType.setScreenType(resultSet.getString("screen_type"));
                screen.setScreenType(screenType);
                Theater theater = new Theater();
                theater.setTheaterId(resultSet.getInt("theater_id"));
                theater.setTheaterName(resultSet.getString("theater_name"));
                screen.setTheater(theater);
                show.setScreen(screen);
                booking.setShow(show);
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                booking.setUser(user);
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                paymentMethod.setPaymentMethod(resultSet.getString("payment_method"));
                booking.setPaymentMethod(paymentMethod);
                booking.setGrandTotal(resultSet.getDouble("grand_total"));
                booking.setNumberOfSeats(resultSet.getInt("number_of_seats"));
                booking.setBookingStatus(BookingStatus.getBookingStatus(resultSet.getString("booking_status")));
                List<BookedShowSeat> bookedShowSeats = getBookedSeats(booking.getBookingId(), booking.getShow().getShowId(), connection);
                booking.setBookedShowSeats(bookedShowSeats);
                booking.setCreatedBy(resultSet.getInt("created_by"));
                booking.setUpdatedBy(resultSet.getInt("updated_by"));
            }
            return booking;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    public List<Booking> getBookingByUser(int userId) throws DBException {
        String query = "SELECT b.*, " +
                "   s.show_date, s.show_time, " +
                "   m.movie_title, m.movie_duration, " +
                "   sc.screen_id, sc.screen_title, " +
                "   st.screen_type, " +
                "   th.theater_id, th.theater_name, " +
                "   u.user_id, u.username, " +
                "   pm.payment_method " +
                "FROM bookings b " +
                "JOIN shows s ON b.show_id = s.show_id " +
                "JOIN movie m ON s.movie_id = m.movie_id " +
                "JOIN screen sc ON s.screen_id = sc.screen_id " +
                "JOIN screen_types st ON sc.screen_type_id = st.screen_type_id " +
                "JOIN theater th ON sc.theater_id = th.theater_id " +
                "JOIN users u ON b.user_id = u.user_id " +
                "JOIN payment_methods pm ON b.payment_method_id = pm.payment_method_id " +
                "WHERE u.user_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Booking> bookings = new ArrayList<>();
        try {
            connection = DBConnection.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                Show show = new Show();
                show.setShowId(resultSet.getInt("show_id"));
                show.setShowDate(resultSet.getDate("show_date").toLocalDate());
                show.setShowTime(resultSet.getTime("show_time").toLocalTime());
                Movie movie = new Movie();
                movie.setMovieTitle(resultSet.getString("movie_title"));
                movie.setMovieDuration(resultSet.getTime("movie_duration").toLocalTime());
                show.setMovie(movie);
                Screen screen = new Screen();
                screen.setScreenId(resultSet.getInt("screen_id"));
                screen.setScreenTitle(resultSet.getString("screen_title"));
                ScreenType screenType = new ScreenType();
                screenType.setScreenType(resultSet.getString("screen_type"));
                screen.setScreenType(screenType);
                Theater theater = new Theater();
                theater.setTheaterId(resultSet.getInt("theater_id"));
                theater.setTheaterName(resultSet.getString("theater_name"));
                screen.setTheater(theater);
                show.setScreen(screen);
                booking.setShow(show);
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                booking.setUser(user);
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                paymentMethod.setPaymentMethod(resultSet.getString("payment_method"));
                booking.setPaymentMethod(paymentMethod);
                booking.setGrandTotal(resultSet.getDouble("grand_total"));
                booking.setNumberOfSeats(resultSet.getInt("number_of_seats"));
                booking.setBookingStatus(BookingStatus.getBookingStatus(resultSet.getString("booking_status")));
                List<BookedShowSeat> bookedShowSeats = getBookedSeats(booking.getBookingId(), booking.getShow().getShowId(), connection);
                booking.setBookedShowSeats(bookedShowSeats);
                booking.setCreatedBy(resultSet.getInt("created_by"));
                booking.setUpdatedBy(resultSet.getInt("updated_by"));
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }

    private List<BookedShowSeat> getBookedSeats(int bookingId, int showId, Connection connection) throws DBException {
        String bookedSeatsQuery = "SELECT * " +
                "FROM show_seats " +
                "WHERE seat_id " +
                "IN (" +
                "   SELECT seat_id " +
                "   FROM booked_seats " +
                "   WHERE booking_id = ?) " +
                "AND show_id = ?";
        List<BookedShowSeat> showSeats = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(bookedSeatsQuery);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.setInt(2, showId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookedShowSeat bookedShowSeat = new BookedShowSeat();
                bookedShowSeat.setShowId(resultSet.getInt("show_id"));
                bookedShowSeat.setSeatId(resultSet.getInt("seat_id"));
                bookedShowSeat.setSeatPrice(resultSet.getDouble("seat_price"));
                Seat seat = seatDAO.getSeatById(bookedShowSeat.getSeatId());
                bookedShowSeat.setRow_num(seat.getRowNum());
                bookedShowSeat.setCol_num(seat.getColNum());
                bookedShowSeat.setSeatType(seat.getSeatCategory().getSeatType());
                showSeats.add(bookedShowSeat);
            }
            return showSeats;
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, null);
        }
    }

    @Override
    public void resetExpiredSeats(int bookingId, int currentUserId) throws DBException {
        Connection connection = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            List<ShowSeat> showSeats = bookedSeatsDAO.getBookedSeatsByBookingId(bookingId);
            List<Integer> seatIds = showSeats.stream().map(ShowSeat::getSeatId).collect(Collectors.toList());
            showSeatDAO.resetShowSeatsQuery(seatIds, connection);
            bookedSeatsDAO.resetBookedSeats(bookingId, connection);
            resetBooking(bookingId, currentUserId, BookingStatus.EXPIRED, connection);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, null, connection);
        }
    }

    @Override
    public void cancelBooking(int bookingId, int currentUserId) throws DBException {
        Connection connection = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            List<ShowSeat> showSeats = bookedSeatsDAO.getBookedSeatsByBookingId(bookingId);
            List<Integer> seatIds = showSeats.stream().map(ShowSeat::getSeatId).collect(Collectors.toList());
            showSeatDAO.resetShowSeatsQuery(seatIds, connection);
            bookedSeatsDAO.resetBookedSeats(bookingId, connection);
            resetBooking(bookingId, currentUserId, BookingStatus.CANCELED, connection);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException(Message.Error.INTERNAL_ERROR, ex);
            }
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, null, connection);
        }
    }

    private void resetBooking(int bookingId, int currentUserId, BookingStatus bookingStatus, Connection connection) {
        String resetBooking = "UPDATE bookings SET booking_status = ?,updated_by = ? WHERE booking_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(resetBooking);
            preparedStatement.setString(1, bookingStatus.getStatus());
            preparedStatement.setInt(2, currentUserId);
            preparedStatement.setInt(3, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}