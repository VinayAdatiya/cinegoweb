package com.cinego.dto.screen;

import com.cinego.model.ScreenType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenResponseDTO {
    private int screenId;
    private String screenTitle;
    private int totalSeats;
    private ScreenType screenType;
    private String layout;
    private int theaterId;
    private String theaterName;

    public ScreenResponseDTO() {
    }

    public ScreenResponseDTO(int screenId, String screenTitle, int totalSeats, ScreenType screenType, String layout, int theaterId, String theaterName) {
        this.screenId = screenId;
        this.screenTitle = screenTitle;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.layout = layout;
        this.theaterId = theaterId;
        this.theaterName = theaterName;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public ScreenType getScreenType() {
        return screenType;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
}
