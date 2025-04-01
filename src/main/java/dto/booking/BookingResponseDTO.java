package dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import common.enums.BookingStatus;
import model.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponseDTO {
    private int bookingId;
    private String movieTitle;
    public int screenId;
    public String screenTitle;
    private Show show;
    private User user;
    private int numberOfSeats;
    private BookingStatus bookingStatus;
    private PaymentMethod paymentMethod;
    private List<BookedShowSeat> bookedShowSeats;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(int bookingId, Show show, User user, int numberOfSeats, BookingStatus bookingStatus, PaymentMethod paymentMethod, List<BookedShowSeat> bookedShowSeats) {
        this.bookingId = bookingId;
        this.show = show;
        this.user = user;
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

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
