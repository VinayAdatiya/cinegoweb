package com.cinego.dto.booking;

import com.cinego.common.enums.BookingStatus;
import com.cinego.model.ShowSeat;

import java.util.List;

public class BookingRequestDTO {
    private int bookingId;
    private int showId;
    private int userId;
    private int numberOfSeats;
    private BookingStatus bookingStatus;
    private int paymentMethodId;
    private List<ShowSeat> showSeatList;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(int bookingId, int showId, int userId, int numberOfSeats, BookingStatus bookingStatus, int paymentMethodId, List<ShowSeat> showSeatList) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.userId = userId;
        this.numberOfSeats = numberOfSeats;
        this.bookingStatus = bookingStatus;
        this.paymentMethodId = paymentMethodId;
        this.showSeatList = showSeatList;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public List<ShowSeat> getShowSeatList() {
        return showSeatList;
    }

    public void setShowSeatList(List<ShowSeat> showSeatList) {
        this.showSeatList = showSeatList;
    }
}
