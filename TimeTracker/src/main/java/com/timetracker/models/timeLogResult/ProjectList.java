package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectList {
    @JsonProperty("projectList")
    private List<Project> projectList;

    // Default constructor
    public ProjectList() {
    }

    // Getter
    public List<Project> getProjectList() {
        return projectList;
    }

    // Setter
    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}