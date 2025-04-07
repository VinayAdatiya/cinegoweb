package com.cinego.dto.booking;

import com.cinego.common.enums.BookingStatus;
import com.cinego.model.BookedShowSeat;
import com.cinego.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponseDTO {
    private int bookingId;
    private int showId;
    private LocalDate showDate;
    private LocalTime showTime;
    private String movieTitle;
    private LocalTime movieDuration;
    private int screenId;
    private String screenTitle;
    private String screenType;
    private int theaterId;
    private String theaterName;
    private int userId;
    private String userName;
    private double grandTotal;
    private int numberOfSeats;
    private BookingStatus bookingStatus;
    private PaymentMethod paymentMethod;
    private List<BookedShowSeat> bookedShowSeats;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(int bookingId, int showId, LocalDate showDate, LocalTime showTime, String movieTitle, LocalTime movieDuration, int screenId, String screenTitle, String screenType, int theaterId, String theaterName, int userId, String userName, double grandTotal, int numberOfSeats, BookingStatus bookingStatus, PaymentMethod paymentMethod, List<BookedShowSeat> bookedShowSeats) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.movieTitle = movieTitle;
        this.movieDuration = movieDuration;
        this.screenId = screenId;
        this.screenTitle = screenTitle;
        this.screenType = screenType;
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        this.userId = userId;
        this.userName = userName;
        this.grandTotal = grandTotal;
        this.numberOfSeats = numberOfSeats;
        this.bookingStatus = bookingStatus;
        this.paymentMethod = paymentMethod;
        this.bookedShowSeats = bookedShowSeats;
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

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalTime getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(LocalTime movieDuration) {
        this.movieDuration = movieDuration;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<BookedShowSeat> getBookedShowSeats() {
        return bookedShowSeats;
    }

    public void setBookedShowSeats(List<BookedShowSeat> bookedShowSeats) {
        this.bookedShowSeats = bookedShowSeats;
    }
}
