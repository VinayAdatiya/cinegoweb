package com.cinego.model;

import java.io.Serializable;
import java.util.Objects;

public class MovieCrewId implements Serializable {
    private int movieId;
    private int crewId;
    private int designationId;

    public MovieCrewId() {
    }

    public MovieCrewId(int movieId, int crewId, int designationId) {
        this.movieId = movieId;
        this.crewId = crewId;
        this.designationId = designationId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCrewId that = (MovieCrewId) o;
        return movieId == that.movieId && crewId == that.crewId && designationId == that.designationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, crewId, designationId);
    }
}