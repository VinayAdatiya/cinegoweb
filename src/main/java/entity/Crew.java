package entity;

public class Crew {
    private int crewId;
    private String crewName;

    public Crew() {
    }

    public Crew(int crewId, String crewName) {
        this.crewId = crewId;
        this.crewName = crewName;
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }
}