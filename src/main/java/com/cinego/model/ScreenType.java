package com.cinego.model;

public class ScreenType {
    public int screenTypeId;
    public String screenType;
    public String screenTypeDescription;

    public ScreenType() {
    }

    public ScreenType(String screenType, String screenTypeDescription) {
        this.screenType = screenType;
        this.screenTypeDescription = screenTypeDescription;
    }

    public int getScreenTypeId() {
        return screenTypeId;
    }

    public void setScreenTypeId(int screenTypeId) {
        this.screenTypeId = screenTypeId;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenTypeDescription() {
        return screenTypeDescription;
    }

    public void setScreenTypeDescription(String screenTypeDescription) {
        this.screenTypeDescription = screenTypeDescription;
    }
}
