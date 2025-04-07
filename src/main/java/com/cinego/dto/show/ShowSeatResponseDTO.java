package com.cinego.dto.show;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowSeatResponseDTO {
    private int showId;
    private int seatId;
    private double seatPrice;
    private boolean isBooked;
    private boolean available;
    private int rowNum;
    private int colNum;
    private int seatCategoryId;

    public ShowSeatResponseDTO(int showId, int seatId, double seatPrice, boolean isBooked, boolean available, int rowNum, int colNum, int seatCategoryId) {
        this.showId = showId;
        this.seatId = seatId;
        this.seatPrice = seatPrice;
        this.isBooked = isBooked;
        this.available = available;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.seatCategoryId = seatCategoryId;
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

    public int getSeatCategoryId() {
        return seatCategoryId;
    }

    public void setSeatCategoryId(int seatCategoryId) {
        this.seatCategoryId = seatCategoryId;
    }
}
