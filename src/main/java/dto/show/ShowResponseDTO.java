package dto.show;

import dto.movie.MovieDTO;
import dto.screen.ScreenResponseDTO;
import model.ShowSeat;
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
    private List<ShowSeat> showSeatList;

    public ShowResponseDTO() {
    }

    public ShowResponseDTO(int showId, MovieDTO movie, ScreenResponseDTO screen, LocalDate showDate, LocalTime showTime, List<ShowPriceCategoryDTO> showPriceCategoryList, List<ShowSeat> showSeatList) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.showTime = showTime;
        this.showPriceCategoryDTOS = showPriceCategoryList;
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

    public List<ShowSeat> getShowSeatList() {
        return showSeatList;
    }

    public void setShowSeatList(List<ShowSeat> showSeatList) {
        this.showSeatList = showSeatList;
    }
}
