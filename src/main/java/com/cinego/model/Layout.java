package com.cinego.model;

import java.util.List;

public class Layout {
    private int rowNum;
    private int colNum;
    private List<Seat> seats;

    public Layout(){}

    public Layout(int rowNum, int colNum, List<Seat> seats) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.seats = seats;
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}