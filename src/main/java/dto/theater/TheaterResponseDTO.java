package dto.theater;

import dto.user.UserResponseDTO;
import model.Address;

public class TheaterResponseDTO {
    private int theaterId;
    private UserResponseDTO theaterAdmin;
    private String theaterName;
    private Double theaterRating;
    private Address theaterAddress;

    public TheaterResponseDTO(){}

    public TheaterResponseDTO(int theaterId, UserResponseDTO theaterAdmin, String theaterName, Double theaterRating, Address theaterAddress) {
        this.theaterId = theaterId;
        this.theaterAdmin = theaterAdmin;
        this.theaterName = theaterName;
        this.theaterRating = theaterRating;
        this.theaterAddress = theaterAddress;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public UserResponseDTO getTheaterAdmin() {
        return theaterAdmin;
    }

    public void setTheaterAdmin(UserResponseDTO theaterAdmin) {
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
}
