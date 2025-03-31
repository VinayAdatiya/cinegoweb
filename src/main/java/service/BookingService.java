package service;

import common.enums.BookingStatus;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IBookingDAO;
import dao.IShowSeatDAO;
import dao.impl.BookingDAOImpl;
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

    private final IBookingDAO bookingDAO = new BookingDAOImpl();
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();
    private final IBookingMapper bookingMapper = Mappers.getMapper(IBookingMapper.class);

    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, int currentUserId) throws ApplicationException {
        double total = 0;
        Booking booking = bookingMapper.toBooking(bookingRequestDTO);
        List<ShowSeat> listOfSeats = bookingRequestDTO.getShowSeatList();
        List<ShowSeat> showSeatList = new ArrayList<>();
        int showId = booking.getShow().getShowId();
        for (ShowSeat seat : listOfSeats) {
            ShowSeat showSeat = showSeatDAO.getShowSeatById(showId, seat.getSeatId());
            showSeatList.add(showSeat);
        }
        for (ShowSeat showSeat : showSeatList) {
            total += showSeat.getSeatPrice();
        }
        double cinegoCommision = total * 0.10;
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

    public BookingResponseDTO confirmBooking(BookingRequestDTO bookingRequestDTO, int currentUserId) throws ApplicationException {
        Booking booking = bookingMapper.toBooking(bookingRequestDTO);
        booking.setBookingId(bookingRequestDTO.getBookingId());
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setUpdatedBy(currentUserId);
        booking = bookingDAO.confirmBooking(booking);
        return bookingMapper.toBookingResponseDTO(booking);
    }

    public BookingResponseDTO getBookingById(int bookingId) throws DBException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new ApplicationException("Booking not found");
        }
        return bookingMapper.toBookingResponseDTO(booking);
    }

    public void resetBooking(int bookingId, int currentUserId) {
        bookingDAO.resetExpiredSeats(bookingId, currentUserId);
    }
}