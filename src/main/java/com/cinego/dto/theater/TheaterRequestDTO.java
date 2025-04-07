package com.cinego.dto.theater;

import com.cinego.dto.user.UserSignUpDTO;
import com.cinego.model.Address;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterRequestDTO {
    private int theaterId;
    private UserSignUpDTO theaterAdmin;
    private String theaterName;
    private Double theaterRating;
    private Address theaterAddress;
    private int createdBy;
    private int updatedBy;

    public TheaterRequestDTO(){}

    public TheaterRequestDTO(int theaterId, UserSignUpDTO theaterAdmin, String theaterName, Double theaterRating, Address theaterAddress, int createdBy, int updatedBy) {
        this.theaterId = theaterId;
        this.theaterAdmin = theaterAdmin;
        this.theaterName = theaterName;
        this.theaterRating = theaterRating;
        this.theaterAddress = theaterAddress;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public UserSignUpDTO getTheaterAdmin() {
        return theaterAdmin;
    }

    public void setTheaterAdmin(UserSignUpDTO theaterAdmin) {
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

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}