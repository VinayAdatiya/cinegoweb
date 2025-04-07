package com.cinego.dto.show;

public class ShowPriceCategoryDTO {
    private int seatCategoryId;
    private double price;

    public ShowPriceCategoryDTO(){}

    public ShowPriceCategoryDTO(int seatCategoryId, double price) {
        this.seatCategoryId = seatCategoryId;
        this.price = price;
    }

    public int getSeatCategoryId() {
        return seatCategoryId;
    }

    public void setSeatCategoryId(int seatCategoryId) {
        this.seatCategoryId = seatCategoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
