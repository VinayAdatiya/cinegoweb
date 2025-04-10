package com.cinego.model;

public class Genre {
    private int genreId;
    private String genreName;

    public Genre() {
    }

    public Genre(int GenreId, String GenreName) {
        this.genreId = GenreId;
        this.genreName = GenreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", genreName='" + genreName + '\'' +
                '}';
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}