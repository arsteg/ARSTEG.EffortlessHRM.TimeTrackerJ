package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskList {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("taskName")
    private String taskName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public TaskList() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}