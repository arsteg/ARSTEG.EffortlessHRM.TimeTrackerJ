package com.timetracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    // Default constructor
    public BaseResponse() {
    }

    // Getters
    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}