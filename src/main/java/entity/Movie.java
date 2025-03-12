package entity;

import utils.DateTimeUtil;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Movie {

    private int movieId;
    private String movieTitle;
    private Float movieRating;
    private LocalTime movieDuration;
    private LocalDate movieReleaseDate;
    private String movieDescription;
    private List<Integer> languageIds;
    private List<Integer> genreIds;
    private List<Integer> formatIds;
    private List<MovieCrewEntry> movieCrewEntries;

    public LocalDate getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(LocalDate movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public LocalTime getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(LocalTime movieDuration) {
        this.movieDuration = movieDuration;
    }

    public Date getSqlMovieReleaseDate(){
        return DateTimeUtil.toSqlDate(this.movieReleaseDate);
    }
    public void setSqlMovieReleaseDate(Date sqlDate){
        this.movieReleaseDate = DateTimeUtil.toLocalDate(sqlDate);
    }

    public Time getSqlMovieDuration(){
        return DateTimeUtil.toSqlTime(this.movieDuration);
    }

    public void setSqlMovieDuration(Time sqlTime){
        this.movieDuration = DateTimeUtil.toLocalTime(sqlTime);
    }

    public Movie() {
    }

    public Movie(int movieId, String movieTitle, Float movieRating, LocalTime movieDuration,
                 LocalDate movieReleaseDate, String movieDescription,
                 List<Integer> languageIds, List<Integer> genreIds,
                 List<Integer> formatIds, List<MovieCrewEntry> movieCrewEntries) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDuration = movieDuration;
        this.movieReleaseDate = movieReleaseDate;
        this.movieDescription = movieDescription;
        this.languageIds = languageIds;
        this.genreIds = genreIds;
        this.formatIds = formatIds;
        this.movieCrewEntries = movieCrewEntries;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieRating=" + movieRating +
                ", movieDuration=" + movieDuration +
                ", movieReleaseDate=" + movieReleaseDate +
                ", movieDescription='" + movieDescription + '\'' +
                ", languageIds=" + languageIds +
                ", genreIds=" + genreIds +
                ", formatIds=" + formatIds +
                ", movieCrewEntries=" + movieCrewEntries +
                '}';
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public List<MovieCrewEntry> getMovieCrewEntries() {
        return movieCrewEntries;
    }

    public void setMovieCrewEntries(List<MovieCrewEntry> movieCrewEntries) {
        this.movieCrewEntries = movieCrewEntries;
    }
}