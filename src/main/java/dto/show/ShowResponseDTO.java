package dto.show;

import dto.movie.MovieDTO;
import dto.screen.ScreenResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowResponseDTO {
    private int showId;
    private MovieDTO movie;
    private ScreenResponseDTO screen;
    private LocalDate showDate;
    private LocalTime showTime;

    public ShowResponseDTO(){

    }

    public ShowResponseDTO(int showId, MovieDTO movie, ScreenResponseDTO screen, LocalDate showDate, LocalTime showTime) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.showTime = showTime;
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
}
