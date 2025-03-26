package dto.screen;

import com.fasterxml.jackson.annotation.JsonInclude;
import dto.theater.TheaterResponseDTO;
import model.ScreenType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenResponseDTO {
    private int screenId;
    private String screenTitle;
    private int totalSeats;
    private ScreenType screenType;
    private String layout;
    private TheaterResponseDTO theater;

    public ScreenResponseDTO() {
    }

    public ScreenResponseDTO(int screenId, String screenTitle, int totalSeats, ScreenType screenType, String layout, TheaterResponseDTO theater) {
        this.screenId = screenId;
        this.screenTitle = screenTitle;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.layout = layout;
        this.theater = theater;
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

    public TheaterResponseDTO getTheater() {
        return theater;
    }

    public void setTheater(TheaterResponseDTO theater) {
        this.theater = theater;
    }
}
