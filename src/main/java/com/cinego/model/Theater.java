package com.cinego.model;

import java.time.LocalDateTime;

public class Theater {
    private int theaterId;
    private User theaterAdmin;
    private String theaterName;
    private Double theaterRating;
    private Address theaterAddress;
    private int createdBy;
    private LocalDateTime createdOn;
    private int updatedBy;
    private LocalDateTime updatedOn;

    public Theater() {
    }

    public Theater(int theaterId, User theaterAdmin, String theaterName, Double theaterRating, Address theaterAddress) {
        this.theaterId = theaterId;
        this.theaterAdmin = theaterAdmin;
        this.theaterName = theaterName;
        this.theaterRating = theaterRating;
        this.theaterAddress = theaterAddress;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "theaterId=" + theaterId +
                ", theaterAdmin=" + theaterAdmin +
                ", theaterName='" + theaterName + '\'' +
                ", theaterRating=" + theaterRating +
                ", theaterAddress=" + theaterAddress +
                '}';
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public User getTheaterAdmin() {
        return theaterAdmin;
    }

    public void setTheaterAdmin(User theaterAdmin) {
        this.theaterAdmin = theaterAdmin;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public Double getTheaterRating() {
        return theaterRating;
    }

    public void setTheaterRating(Double theaterRating) {
        this.theaterRating = theaterRating;
    }

    public Address getTheaterAddress() {
        return theaterAddress;
    }

    public void setTheaterAddress(Address theaterAddress) {
        this.theaterAddress = theaterAddress;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}