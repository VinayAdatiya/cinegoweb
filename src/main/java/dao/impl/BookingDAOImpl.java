package dao.impl;

import common.Message;
import common.enums.BookingStatus;
import common.exception.DBException;
import config.DBConnection;
import dao.*;
import model.Booking;
import model.ShowSeat;
import java.sql.*;
import java.util.List;

public class BookingDAOImpl implements IBookingDAO {

    private final IShowDAO showDAO = new ShowDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IPaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();
    private final IBookedSeatsDAO bookedSeatsDAO = new BookedSeatsDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();

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
        int generatedBookingId = 0;

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
            DBConnection.closeResources(null, updateShowSeatPreparedStatement, null);
        }
    }

    @Override
    public Booking confirmBooking(Booking booking) throws DBException {
        String updateBookingQuery = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        String clearHoldUntilQuery = "UPDATE show_seats SET hold_until = NULL WHERE show_id = ? AND seat_id = ?";
        String confirmBooking = "SELECT FROM bookings";
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
            updateBookingPreparedStatement.setInt(2, booking.getBookingId());
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
                booking.setCreatedBy(resultSet.getInt("created_by"));
                booking.setUpdatedBy(resultSet.getInt("updated_by"));
            }
            System.out.println(booking);
            return booking;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(resultSet, preparedStatement, connection);
        }
    }


    @Override
    public void resetExpiredSeats(int bookingId, int currentUserId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            showSeatDAO.resetShowSeatsQuery(connection);
            bookedSeatsDAO.resetBookedSeats(bookingId, connection);
            resetBooking(bookingId, connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, connection);
        }
    }

    private void resetBooking(int bookingId, Connection connection) {
        String resetBooking = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement.setString(1, BookingStatus.EXPIRED.toString());
            preparedStatement.setInt(2, bookingId);
            preparedStatement = connection.prepareStatement(resetBooking);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnection.closeResources(null, preparedStatement, null);
        }
    }
}