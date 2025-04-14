package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewtaskData {
    @JsonProperty("newTask")
    private ProjectTask newTask;

    // Default constructor
    public NewtaskData() {
    }

    // Getter
    public ProjectTask getNewTask() {
        return newTask;
    }

    // Setter
    public void setNewTask(ProjectTask newTask) {
        this.newTask = newTask;
    }
}