package com.timetracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public  class ApplicationLogResult {
    @JsonProperty("status")
    private String status;

    @JsonProperty("body")
    private ApplicationLog body;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApplicationLog getBody() {
        return body;
    }

    public void setBody(ApplicationLog body) {
        this.body = body;
    }
}