// entity/MovieCrewEntry.java
package entity;

import java.util.List;

public class MovieCrewEntry {

    private int movieId;
    private int crewId;
    private int designationId;
    private String characterName;

    public MovieCrewEntry() {
    }

    public MovieCrewEntry(int movieId, int crewId, int designationId, String characterName) {
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

    public static class MovieCrewEntryList {
        private int movieId;
        private List<MovieCrewEntry> movieCrewEntries;

        public MovieCrewEntryList() {
        }

        public MovieCrewEntryList(int movieId, List<MovieCrewEntry> movieCrewEntries) {
            this.movieId = movieId;
            this.movieCrewEntries = movieCrewEntries;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public List<MovieCrewEntry> getMovieCrewEntries() {
            return movieCrewEntries;
        }

        public void setMovieCrewEntries(List<MovieCrewEntry> movieCrewEntries) {
            this.movieCrewEntries = movieCrewEntries;
        }
    }
}