package com.cinego.model;

public class SeatCategory {
    private int seatCategoryId;
    private String seatType;

    public SeatCategory(){}

    public SeatCategory(int seatCategoryId, String seatType) {
        this.seatCategoryId = seatCategoryId;
        this.seatType = seatType;
    }

    public int getSeatCategoryId() {
        return seatCategoryId;
    }

    public void setSeatCategoryId(int seatCategoryId) {
        this.seatCategoryId = seatCategoryId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}