package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveImageRequest {
    @JsonProperty("fileString")
    private String fileString;

    // Default constructor
    public LiveImageRequest() {
    }

    // Getter
    public String getFileString() {
        return fileString;
    }

    // Setter
    public void setFileString(String fileString) {
        this.fileString = fileString;
    }
}