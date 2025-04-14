package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ProjectRequest {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("estimatedTime")
    private String estimatedTime;

    // Default constructor
    public ProjectRequest() {
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getNotes() {
        return notes;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}