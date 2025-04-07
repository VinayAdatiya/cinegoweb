package com.cinego.model;

import java.time.LocalDateTime;

public class ShowSeat {
    private int showId;
    private int seatId;
    private double seatPrice;
    private boolean isBooked;
    private boolean available;
    private LocalDateTime createdOn;
    private int createdBy;
    private LocalDateTime updatedOn;
    private int updatedBy;

    private int rowNum;
    private int colNum;
    private SeatCategory seatCategory;

    public ShowSeat() {
    }

    public ShowSeat(int showId, int seatId, double seatPrice, boolean isBooked, boolean available, LocalDateTime createdOn, int createdBy, LocalDateTime updatedOn, int updatedBy, int rowNum, int colNum, SeatCategory seatCategory) {
        this.showId = showId;
        this.seatId = seatId;
        this.seatPrice = seatPrice;
        this.isBooked = isBooked;
        this.available = available;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.seatCategory = seatCategory;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public SeatCategory getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategory(SeatCategory seatCategory) {
        this.seatCategory = seatCategory;
    }
}
