package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CurrentWeekTotalTime {
    @JsonProperty("user")
    private String user;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    // Default constructor
    public CurrentWeekTotalTime() {
    }

    // Getters
    public String getUser() {
        return user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    // Setters
    public void setUser(String user) {
        this.user = user;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}