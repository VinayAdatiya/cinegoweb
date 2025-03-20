package dto.movie;

import model.MovieCrew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MovieRequestDTO {
    private String movieTitle;
    private Float movieRating;
    private LocalTime movieDuration;
    private LocalDate movieReleaseDate;
    private String movieDescription;
    private String moviePosterPath;
    private List<Integer> languageIds;
    private List<Integer> genreIds;
    private List<Integer> formatIds;
    private List<MovieCrew> movieCrewEntries;

    public MovieRequestDTO() {

    }

    public MovieRequestDTO(String movieTitle, Float movieRating, LocalTime movieDuration, LocalDate movieReleaseDate, String movieDescription, String moviePosterPath, List<Integer> languageIds, List<Integer> genreIds, List<Integer> formatIds, List<MovieCrew> movieCrewEntries) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDuration = movieDuration;
        this.movieReleaseDate = movieReleaseDate;
        this.movieDescription = movieDescription;
        this.moviePosterPath = moviePosterPath;
        this.languageIds = languageIds;
        this.genreIds = genreIds;
        this.formatIds = formatIds;
        this.movieCrewEntries = movieCrewEntries;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Float getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Float movieRating) {
        this.movieRating = movieRating;
    }

    public LocalTime getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(LocalTime movieDuration) {
        this.movieDuration = movieDuration;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public LocalDate getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(LocalDate movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public List<Integer> getLanguageIds() {
        return languageIds;
    }

    public void setLanguageIds(List<Integer> languageIds) {
        this.languageIds = languageIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getFormatIds() {
        return formatIds;
    }

    public void setFormatIds(List<Integer> formatIds) {
        this.formatIds = formatIds;
    }

    public List<MovieCrew> getMovieCrewEntries() {
        return movieCrewEntries;
    }

    public void setMovieCrewEntries(List<MovieCrew> movieCrewEntries) {
        this.movieCrewEntries = movieCrewEntries;
    }
}
