package com.cinego.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "crew")
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id")
    private int crewId;

    @Column(name = "crew_name")
    private String crewName;

    public Crew() {
    }

    public Crew(int crewId, String crewName) {
        this.crewId = crewId;
        this.crewName = crewName;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "crewId=" + crewId +
                ", crewName='" + crewName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return crewId == crew.crewId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewId);
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