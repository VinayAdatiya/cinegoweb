package model;

import common.enums.BookingStatus;

public class Booking {
    private int bookingId;
    private Show show;
    private User user;
    private double grandTotal;
    private int numberOfSeats;
    private BookingStatus bookingStatus;
    private PaymentMethod paymentMethod;
    private int createdBy;
    private int updatedBy;

    public Booking() {
    }

    public Booking(int bookingId, Show show, User user, double grandTotal, int numberOfSeats, BookingStatus bookingStatus, PaymentMethod paymentMethod, int createdBy, int updatedBy) {
        this.bookingId = bookingId;
        this.show = show;
        this.user = user;
        this.grandTotal = grandTotal;
        this.numberOfSeats = numberOfSeats;
        this.bookingStatus = bookingStatus;
        this.paymentMethod = paymentMethod;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}
