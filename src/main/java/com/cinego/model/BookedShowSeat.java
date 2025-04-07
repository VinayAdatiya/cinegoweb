package com.cinego.model;

public class BookedShowSeat {
    private int showId;
    private int seatId;
    private double seatPrice;
    private int row_num;
    private int col_num;
    private String seatType;

    public BookedShowSeat() {
    }

    public BookedShowSeat(int showId, int seatId, double seatPrice, int row_num, int col_num, String seatType) {
        this.showId = showId;
        this.seatId = seatId;
        this.seatPrice = seatPrice;
        this.row_num = row_num;
        this.col_num = col_num;
        this.seatType = seatType;
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

    public int getRow_num() {
        return row_num;
    }

    public void setRow_num(int row_num) {
        this.row_num = row_num;
    }

    public int getCol_num() {
        return col_num;
    }

    public void setCol_num(int col_num) {
        this.col_num = col_num;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
