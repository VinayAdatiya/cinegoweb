package model;

public class ShowPriceCategory {
    private Show show;
    private SeatCategory seatCategory;
    private double basePrice;

    public ShowPriceCategory() {
    }

    public ShowPriceCategory(Show show, SeatCategory seatCategory, int basePrice) {
        this.show = show;
        this.seatCategory = seatCategory;
        this.basePrice = basePrice;
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

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
