package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskRequest {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("skip")
    private String skip;

    @JsonProperty("next")
    private String next;

    // Default constructor
    public TaskRequest() {
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getSkip() {
        return skip;
    }

    public String getNext() {
        return next;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public void setNext(String next) {
        this.next = next;
    }
}