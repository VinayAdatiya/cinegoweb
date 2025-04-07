package com.cinego.dto.screen;

import com.cinego.model.ScreenType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.cinego.model.Theater;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenRequestDTO {
    private int screenId;
    private String screenTitle;
    private int totalSeats;
    private ScreenType screenType;
    private String layout;
    private Theater theater;
    private int createdBy;
    private int updatedBy;

    public ScreenRequestDTO() {
    }

    public ScreenRequestDTO(int screenId, String screenTitle, int totalSeats, ScreenType screenType, String layout, Theater theater, int createdBy, int updatedBy) {
        this.screenId = screenId;
        this.screenTitle = screenTitle;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.layout = layout;
        this.theater = theater;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
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
