package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskUser {
    @JsonProperty("user")
    private String user;

    // Default constructor
    public TaskUser() {
    }

    // Getter
    public String getUser() {
        return user;
    }

    // Setter
    public void setUser(String user) {
        this.user = user;
    }
}