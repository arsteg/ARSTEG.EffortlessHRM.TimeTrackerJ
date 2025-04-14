package com.timetracker.models;



import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorLog {
    @JsonProperty("_id")
    private String id;

    // Add fields as needed
    // Default constructor
    public ErrorLog() {
    }

    // Getter
    public String getId() {
        return id;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }
}