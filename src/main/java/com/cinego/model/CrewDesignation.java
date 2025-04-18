package com.cinego.model;

import jakarta.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "crew_designation")
public class CrewDesignation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designation_id")
    private int designationId;

    @Column(name = "designation_title")
    private String designationName;

    public CrewDesignation() {
    }

    public CrewDesignation(int designationId, String designationName) {
        this.designationId = designationId;
        this.designationName = designationName;
    }

    @Override
    public String toString() {
        return "CrewDesignation{" +
                "designationId=" + designationId +
                ", designationName='" + designationName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewDesignation that = (CrewDesignation) o;
        return designationId == that.designationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(designationId);
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
}