package model;

public class Seat {
    private int seatId;
    private Screen screen;
    private int rowNum;
    private int colNum;
    private SeatCategory seatCategory;

    public Seat() {

    }

    public Seat(int seatId, Screen screen, int rowNum, int colNum, SeatCategory seatCategory) {
        this.seatId = seatId;
        this.screen = screen;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.seatCategory = seatCategory;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
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