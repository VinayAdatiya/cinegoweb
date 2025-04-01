package dao.impl;

import common.Message;
import common.enums.BookingStatus;
import common.exception.DBException;
import config.DBConnection;
import dao.*;
import model.BookedShowSeat;
import model.Booking;
import model.Seat;
import model.ShowSeat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDAOImpl implements IBookingDAO {

    private final IShowDAO showDAO = new ShowDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IPaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();
    private final IBookedSeatsDAO bookedSeatsDAO = new BookedSeatsDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();
    private final ISeatDAO seatDAO = new SeatDAOImpl();

    @Override
    public Booking createBooking(Booking booking, List<ShowSeat> showSeats) throws DBException {
        String bookingQuery = "INSERT INTO bookings " +
                "(show_id, user_id, grand_total, number_of_seats, booking_status, payment_method_id, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String bookedSeatsQuery = "INSERT INTO booked_seats (booking_id, seat_id) VALUES (?, ?)";
        String updateShowSeatQuery = "UPDATE show_seats SET available = 0, hold_until = CURRENT_TIMESTAMP + INTERVAL 3 MINUTE " +
                "WHERE show_id = ? AND seat_id = ?";

        Connection connection = null;
        PreparedStatement bookingPreparedStatement = null;
        PreparedStatement bookedSeatsPreparedStatement = null;
        PreparedStatement updateShowSeatPreparedStatement = null;
        ResultSet generatedKeys = null;
        int generatedBookingId;

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
                generatedBookingId = generatedKeys.getInt(1);
                booking.setBookingId(generatedBookingId);
            } else {
                throw new SQLException("Failed to retrieve generated booking ID.");
            }
            // 2. Reserve Seats
            bookedSeatsPreparedStatement = connection.prepareStatement(bookedSeatsQuery);
            updateShowSeatPreparedStatement = connection.prepareStatement(updateShowSeatQuery);
            for (ShowSeat showSeat : showSeats) {
                // Insert into booked_seats
                bookedSeatsPreparedStatement.setInt(1, generatedBookingId);
                bookedSeatsPreparedStatement.setInt(2, showSeat.getSeatId());
                bookedSeatsPreparedStatement.executeUpdate();
                // Update into show_seats
                updateShowSeatPreparedStatement.setInt(1, showSeat.getShowId());
                updateShowSeatPreparedStatement.setInt(2, showSeat.getSeatId());
                updateShowSeatPreparedStatement.executeUpdate();
            }
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
            DBConnection.closeResources(null, bookedSeatsPreparedStatement, null);
            DBConnection.closeResources(null, updateShowSeatPreparedStatement, null);
        }
    }

    @Override
    public Booking confirmBooking(Booking booking) throws DBException {
        String updateBookingQuery = "UPDATE bookings SET booking_status = ? , is_booked = ? WHERE booking_id = ?";
        String clearHoldUntilQuery = "UPDATE show_seats SET hold_until = NULL WHERE show_id = ? AND seat_id = ?";
        Connection connection = null;
        PreparedStatement updateBookingPreparedStatement = null;
        PreparedStatement clearHoldUntilPreparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            int bookingId = booking.getBookingId();
            booking = getBookingById(bookingId);
            // 1. Update Booking Status
            updateBookingPreparedStatement = connection.prepareStatement(updateBookingQuery);
            updateBookingPreparedStatement.setString(1, booking.getBookingStatus().toString());
            updateBookingPreparedStatement.setBoolean(2, true);
            updateBookingPreparedStatement.setInt(3, booking.getBookingId());
            updateBookingPreparedStatement.executeUpdate();
            // 2. Clear hold_until for booked seats
            List<ShowSeat> showSeats = bookedSeatsDAO.getBookedSeatsByBookingId(booking.getBookingId());
            clearHoldUntilPreparedStatement = connection.prepareStatement(clearHoldUntilQuery);
            for (ShowSeat showSeat : showSeats) {
                clearHoldUntilPreparedStatement.setInt(1, booking.getShow().getShowId());
                clearHoldUntilPreparedStatement.setInt(2, showSeat.getSeatId());
                clearHoldUntilPreparedStatement.executeUpdate();
            }
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
            DBConnection.closeResources(null, updateBookingPreparedStatement, connection);
            DBConnection.closeResources(null, clearHoldUntilPreparedStatement, null);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) throws DBException {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
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
                booking.setShow(showDAO.getShowById(resultSet.getInt("show_id")));
                booking.setUser(userDAO.getUserById(resultSet.getInt("user_id")));
                booking.setPaymentMethod(paymentMethodDAO.getPaymentMethodById(resultSet.getInt("payment_method_id")));
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