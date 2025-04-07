package com.cinego.dto.show;

import com.cinego.dto.movie.MovieDTO;
import com.cinego.dto.screen.ScreenResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ShowResponseDTO {
    private int showId;
    private MovieDTO movie;
    private ScreenResponseDTO screen;
    private LocalDate showDate;
    private LocalTime showTime;
    private List<ShowPriceCategoryDTO> showPriceCategoryDTOS;
    private List<ShowSeatResponseDTO> showSeatList;

    public ShowResponseDTO() {
    }

    public ShowResponseDTO(int showId, MovieDTO movie, ScreenResponseDTO screen, LocalDate showDate, LocalTime showTime, List<ShowPriceCategoryDTO> showPriceCategoryDTOS, List<ShowSeatResponseDTO> showSeatList) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.showTime = showTime;
        this.showPriceCategoryDTOS = showPriceCategoryDTOS;
        this.showSeatList = showSeatList;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public ScreenResponseDTO getScreen() {
        return screen;
    }

    public void setScreen(ScreenResponseDTO screen) {
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

    public List<ShowPriceCategoryDTO> getShowPriceCategoryList() {
        return showPriceCategoryDTOS;
    }

    public void setShowPriceCategoryList(List<ShowPriceCategoryDTO> showPriceCategoryList) {
        this.showPriceCategoryDTOS = showPriceCategoryList;
    }

    public List<ShowSeatResponseDTO> getShowSeatList() {
        return showSeatList;
    }

    public void setShowSeatList(List<ShowSeatResponseDTO> showSeatList) {
        this.showSeatList = showSeatList;
    }
}
