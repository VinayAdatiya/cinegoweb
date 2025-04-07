package com.cinego.model;

public class CrewDesignation {
    private int designationId;
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