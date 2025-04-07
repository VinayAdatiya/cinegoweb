package com.cinego.dto.show;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowRequestDTO {
    private int movieId;
    private int screenId;
    private LocalDate showDate;
    private LocalTime showTime;
    private int createdBy;
    private int updatedBy;
    private List<ShowPriceCategoryDTO> showPriceCategoryDTOS;

    public ShowRequestDTO(){

    }

    public ShowRequestDTO(int movieId, int screenId, LocalDate showDate, LocalTime showTime, int createdBy, int updatedBy, List<ShowPriceCategoryDTO> showPriceCategoryDTOS) {
        this.movieId = movieId;
        this.screenId = screenId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.showPriceCategoryDTOS = showPriceCategoryDTOS;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
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

    public List<ShowPriceCategoryDTO> getShowPriceCategoryDTOS() {
        return showPriceCategoryDTOS;
    }

    public void setShowPriceCategoryDTOS(List<ShowPriceCategoryDTO> showPriceCategoryDTOS) {
        this.showPriceCategoryDTOS = showPriceCategoryDTOS;
    }
}
