package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("projectName")
    private String projectName;

    // Default constructor
    public Project() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}