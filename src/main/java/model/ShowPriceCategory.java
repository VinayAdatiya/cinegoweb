package model;

public class ShowPriceCategory {
    private Show show;
    private SeatCategory seatCategory;
    private int base_price;

    public ShowPriceCategory(){}

    public ShowPriceCategory(Show show, SeatCategory seatCategory, int base_price) {
        this.show = show;
        this.seatCategory = seatCategory;
        this.base_price = base_price;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public SeatCategory getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategory(SeatCategory seatCategory) {
        this.seatCategory = seatCategory;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }
}
