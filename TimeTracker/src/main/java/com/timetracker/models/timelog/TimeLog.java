package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TimeLog extends TimeLogBase {
    @JsonProperty("task")
    private String task;

    @JsonProperty("StartDate")
    private LocalDateTime startDate;

    @JsonProperty("fileString")
    private String fileString;

    @JsonProperty("project")
    private String project;

    @JsonProperty("machineId")
    private String machineId;

    @JsonProperty("makeThisDeviceActive")
    private boolean makeThisDeviceActive;

    @JsonProperty("message")
    private String message;

    // Default constructor
    public TimeLog() {
    }

    // Getters
    public String getTask() {
        return task;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getFileString() {
        return fileString;
    }

    public String getProject() {
        return project;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isMakeThisDeviceActive() {
        return makeThisDeviceActive;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setTask(String task) {
        this.task = task;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setMakeThisDeviceActive(boolean makeThisDeviceActive) {
        this.makeThisDeviceActive = makeThisDeviceActive;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}