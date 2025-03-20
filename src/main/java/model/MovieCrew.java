package model;

public class MovieCrew {

    private int movieId;
    private Crew crew;
    private CrewDesignation crewDesignation;
    private String characterName;

    public MovieCrew() {
    }

    public MovieCrew(int movieId, Crew crew, CrewDesignation crewDesignation, String characterName) {
        this.movieId = movieId;
        this.crew = crew;
        this.crewDesignation = crewDesignation;
        this.characterName = characterName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
}