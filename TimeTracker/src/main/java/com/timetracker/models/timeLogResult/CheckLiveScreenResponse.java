package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckLiveScreenResponse {
    @JsonProperty("Success")
    private boolean success;

    // Default constructor
    public CheckLiveScreenResponse() {
    }

    // Getter
    public boolean isSuccess() {
        return success;
    }

    // Setter
    public void setSuccess(boolean success) {
        this.success = success;
    }
}