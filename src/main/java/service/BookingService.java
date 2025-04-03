package service;

import common.Message;
import common.enums.BookingStatus;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IBookingDAO;
import dao.ISeatDAO;
import dao.IShowDAO;
import dao.IShowSeatDAO;
import dao.impl.BookingDAOImpl;
import dao.impl.SeatDAOImpl;
import dao.impl.ShowDAOImpl;
import dao.impl.ShowSeatDAOImpl;
import dto.booking.BookingRequestDTO;
import dto.booking.BookingResponseDTO;
import mapper.IBookingMapper;
import model.Booking;
import model.ShowSeat;
import org.mapstruct.factory.Mappers;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final IShowDAO showDAO = new ShowDAOImpl();
    private final ISeatDAO seatDAO = new SeatDAOImpl();
    private final IBookingDAO bookingDAO = new BookingDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();
    private final IBookingMapper bookingMapper = Mappers.getMapper(IBookingMapper.class);

    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, int currentUserId) throws ApplicationException {
        double total = 0;
        Booking booking = bookingMapper.toBooking(bookingRequestDTO);
        List<ShowSeat> listOfSeats = bookingRequestDTO.getShowSeatList();
        List<ShowSeat> showSeatList = new ArrayList<>();
        int showId = booking.getShow().getShowId();
        showDAO.checkShowTiming(showId); // Validate Show is Ended or not
        // Calculating Grand Total & Validating Each Seat is Available for booking or not
        for (ShowSeat seat : listOfSeats) {
            ShowSeat showSeat = showSeatDAO.getShowSeatById(showId, seat.getSeatId());
            seatDAO.checkSeatType(seat.getSeatId());
            if (showSeat.isAvailable()) {
                showSeatList.add(showSeat);
            } else {
                throw new ApplicationException(Message.Error.SEAT_NOT_AVAILABLE);
            }
        }
        for (ShowSeat showSeat : showSeatList) {
            total += showSeat.getSeatPrice();
        }
        double cinegoCommision = total * 0.02;
        double cgst = total * 0.18;
        double sgst = total * 0.18;
        double grandTotal = total + cinegoCommision + cgst + sgst;
        booking.setGrandTotal(grandTotal);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedBy(currentUserId);
        booking.setUpdatedBy(currentUserId);
        booking = bookingDAO.createBooking(booking, showSeatList);
        return bookingMapper.toBookingResponseDTO(booking);
    }

    public BookingResponseDTO confirmBooking(int bookingId, int currentUserId) throws ApplicationException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        validateBooking(booking, currentUserId);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setUpdatedBy(currentUserId);
        booking = bookingDAO.confirmBooking(booking);
        return bookingMapper.toBookingResponseDTO(booking);
    }

    private void validateBooking(Booking booking, int currentUserId) throws ApplicationException {
        if (booking == null) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new ApplicationException(Message.Error.INVALID_BOOKING_STATUS);
        }
        if (booking.getUser().getUserId() != currentUserId) {
            throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
        }
    }

    public BookingResponseDTO getBookingById(int bookingId) throws DBException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return bookingMapper.toBookingResponseDTO(booking);
    }

    public void resetBooking(int bookingId, int currentUserId) {
        bookingDAO.resetExpiredSeats(bookingId, currentUserId);
    }

    public void cancelBooking(int bookingId, int currentUserId) {
        bookingDAO.cancelBooking(bookingId, currentUserId);
    }
}