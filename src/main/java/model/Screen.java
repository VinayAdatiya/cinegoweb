package model;

import java.time.LocalDateTime;

public class Screen {
    public int screenId;
    public String screenTitle;
    public int totalSeats;
    public ScreenType screenType;
    public String layout;
    public Theater theater;
    public LocalDateTime createdOn;
    public int createdBy;
    public LocalDateTime updatedOn;
    public int updatedBy;

    public Screen() {
    }

    public Screen(int screenId, String screenTitle, int totalSeats, ScreenType screenType, String layout, Theater theater, LocalDateTime createdOn, int createdBy, LocalDateTime updatedOn, int updatedBy) {
        this.screenId = screenId;
        this.screenTitle = screenTitle;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.layout = layout;
        this.theater = theater;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}