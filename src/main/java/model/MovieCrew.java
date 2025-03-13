package model;

public class MovieCrew {

    private int movieId;
    private int crewId;
    private int designationId;
    private String characterName;

    public MovieCrew() {
    }

    public MovieCrew(int movieId, int crewId, int designationId, String characterName) {
        this.movieId = movieId;
        this.crewId = crewId;
        this.designationId = designationId;
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

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}