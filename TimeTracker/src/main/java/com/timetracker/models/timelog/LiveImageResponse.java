package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveImageResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private String data;

    // Default constructor
    public LiveImageResponse() {
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(String data) {
        this.data = data;
    }
}