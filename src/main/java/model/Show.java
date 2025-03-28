package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Show {
    private int showId;
    private Movie movie;
    private Screen screen;
    private LocalDate showDate;
    private LocalTime showTime;
    private LocalDateTime createdOn;
    private int createdBy;
    private LocalDateTime updatedOn;
    private int updatedBy;
    private List<ShowPriceCategory> showPriceCategoryList;

    public Show() {
    }

    public Show(int showId, Movie movie, Screen screen, LocalDate showDate, LocalTime showTime, LocalDateTime createdOn, int createdBy, LocalDateTime updatedOn, int updatedBy, List<ShowPriceCategory> showPriceCategoryList) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.showTime = showTime;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
        this.showPriceCategoryList = showPriceCategoryList;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
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

    public List<ShowPriceCategory> getShowPriceCategoryList() {
        return showPriceCategoryList;
    }

    public void setShowPriceCategoryList(List<ShowPriceCategory> showPriceCategoryList) {
        this.showPriceCategoryList = showPriceCategoryList;
    }
}
