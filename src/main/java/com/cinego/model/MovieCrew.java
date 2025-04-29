package com.cinego.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "movie_crew")
@IdClass(MovieCrewId.class)
public class MovieCrew {

    @Id
    @Column(name = "movie_id")
    private int movieId;

    @Id
    @Column(name = "crew_id")
    private int crewId;

    @Id
    @Column(name = "designation_id")
    private int designationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "movie_id", insertable = false, updatable = false, referencedColumnName = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "crew_id", insertable = false, updatable = false, referencedColumnName = "crew_id")
    private Crew crew;

    @ManyToOne
    @JoinColumn(name = "designation_id", insertable = false, updatable = false, referencedColumnName = "designation_id")
    private CrewDesignation crewDesignation;

    @Column(name = "character_name")
    private String characterName;

    public MovieCrew() {
    }

    public MovieCrew(int movieId, int crewId, int designationId, Movie movie, Crew crew, CrewDesignation crewDesignation, String characterName) {
        this.movieId = movieId;
        this.crewId = crewId;
        this.designationId = designationId;
        this.movie = movie;
        this.crew = crew;
        this.crewDesignation = crewDesignation;
        this.characterName = characterName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, crew, crewDesignation, characterName);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public CrewDesignation getCrewDesignation() {
        return crewDesignation;
    }

    public void setCrewDesignation(CrewDesignation crewDesignation) {
        this.crewDesignation = crewDesignation;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    @Override
    public String toString() {
        return "MovieCrew{" +
                "movieId=" + movieId +
                ", crewId=" + crewId +
                ", designationId=" + designationId +
                ", crew=" + crew +
                ", crewDesignation=" + crewDesignation +
                ", characterName='" + characterName + '\'' +
                '}';
    }
}