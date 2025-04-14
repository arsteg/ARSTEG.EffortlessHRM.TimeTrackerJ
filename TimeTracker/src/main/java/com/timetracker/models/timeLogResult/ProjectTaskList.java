package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectTaskList {
    @JsonProperty("taskList")
    private List<ProjectTask> taskList;

    // Default constructor
    public ProjectTaskList() {
    }

    // Getter
    public List<ProjectTask> getTaskList() {
        return taskList;
    }

    // Setter
    public void setTaskList(List<ProjectTask> taskList) {
        this.taskList = taskList;
    }
}