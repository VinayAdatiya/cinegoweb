package com.cinego.model;

import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.DateTimeUtil;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Movie {
    private int movieId;
    private String movieTitle;
    private Float movieRating;
    private LocalTime movieDuration;
    private LocalDate movieReleaseDate;
    private String movieDescription;
    private String moviePosterPath;
    private List<Language> languages;
    private List<Genre> genres;
    private List<Format> formats;
    private List<MovieCrew> movieCrewEntries;
    private int createdBy;
    private LocalDateTime createdOn;
    private int updatedBy;
    private LocalDateTime updatedOn;

    public Movie() {
    }

    public Movie(int movieId, String movieTitle, Float movieRating, LocalTime movieDuration, LocalDate movieReleaseDate, String movieDescription, String moviePosterPath, List<Language> languages, List<Genre> genres, List<Format> formats, List<MovieCrew> movieCrewEntries, int createdBy, LocalDateTime createdOn, int updatedBy, LocalDateTime updatedOn) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDuration = movieDuration;
        this.movieReleaseDate = movieReleaseDate;
        this.movieDescription = movieDescription;
        this.moviePosterPath = moviePosterPath;
        this.languages = languages;
        this.genres = genres;
        this.formats = formats;
        this.movieCrewEntries = movieCrewEntries;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
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
                ", moviePosterPath='" + moviePosterPath + '\'' +
                ", languages=" + languages +
                ", genres=" + genres +
                ", formats=" + formats +
                ", movieCrewEntries=" + movieCrewEntries +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
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

    public LocalTime getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(LocalTime movieDuration) {
        this.movieDuration = movieDuration;
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

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public List<MovieCrew> getMovieCrewEntries() {
        return movieCrewEntries;
    }

    public void setMovieCrewEntries(List<MovieCrew> movieCrewEntries) {
        this.movieCrewEntries = movieCrewEntries;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Time getSqlMovieDuration() {
        return DateTimeUtil.toSqlTime(this.getMovieDuration());
    }

    public Date getSqlMovieReleaseDate() {
        return DateTimeUtil.toSqlDate(this.getMovieReleaseDate());
    }

    public List<Integer> getLanguageIds() {
        if (languages != null) {
            return languages.stream().map(Language::getLanguageId).collect(Collectors.toList());
        } else {
            throw new DBException(Message.Error.INTERNAL_ERROR, null);
        }
    }

    public List<Integer> getGenreIds() {
        if (genres != null) {
            return genres.stream().map(Genre::getGenreId).collect(Collectors.toList());
        } else {
            throw new DBException(Message.Error.INTERNAL_ERROR, null);
        }
    }

    public List<Integer> getFormatIds() {
        if (formats != null) {
            return formats.stream().map(Format::getFormatId).collect(Collectors.toList());
        } else {
            throw new DBException(Message.Error.INTERNAL_ERROR, null);
        }
    }
}