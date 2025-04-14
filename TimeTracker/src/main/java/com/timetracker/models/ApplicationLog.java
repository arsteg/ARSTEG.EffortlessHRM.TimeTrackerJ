package com.timetracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ApplicationLog {
    @JsonProperty("appWebsite")
    private String appWebsite;

    @JsonProperty("ModuleName")
    private String moduleName;

    @JsonProperty("ApplicationTitle")
    private String applicationTitle;

    @JsonProperty("TimeSpent")
    private double timeSpent;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("type")
    private String type;

    @JsonProperty("projectReference")
    private String projectReference;

    @JsonProperty("userReference")
    private String userReference;

    @JsonProperty("mouseClicks")
    private int mouseClicks;

    @JsonProperty("keyboardStrokes")
    private int keyboardStrokes;

    @JsonProperty("scrollingNumber")
    private int scrollingNumber;

    @JsonProperty("inactive")
    private double inactive;

    @JsonProperty("total")
    private double total;

    @JsonProperty("_id")
    private String id;

    // Default constructor
    public ApplicationLog() {
    }

    // Getters
    public String getAppWebsite() {
        return appWebsite;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getProjectReference() {
        return projectReference;
    }

    public String getUserReference() {
        return userReference;
    }

    public int getMouseClicks() {
        return mouseClicks;
    }

    public int getKeyboardStrokes() {
        return keyboardStrokes;
    }

    public int getScrollingNumber() {
        return scrollingNumber;
    }

    public double getInactive() {
        return inactive;
    }

    public double getTotal() {
        return total;
    }

    public String getId() {
        return id;
    }

    // Setters
    public void setAppWebsite(String appWebsite) {
        this.appWebsite = appWebsite;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProjectReference(String projectReference) {
        this.projectReference = projectReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public void setMouseClicks(int mouseClicks) {
        this.mouseClicks = mouseClicks;
    }

    public void setKeyboardStrokes(int keyboardStrokes) {
        this.keyboardStrokes = keyboardStrokes;
    }

    public void setScrollingNumber(int scrollingNumber) {
        this.scrollingNumber = scrollingNumber;
    }

    public void setInactive(double inactive) {
        this.inactive = inactive;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setId(String id) {
        this.id = id;
    }
}