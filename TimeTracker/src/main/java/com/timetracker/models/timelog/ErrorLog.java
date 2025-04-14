package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorLog {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("id")
    private String idDuplicate;

    @JsonProperty("error")
    private String error;

    @JsonProperty("details")
    private String details;

    @JsonProperty("createdOn")
    private LocalDateTime createdOn;

    @JsonProperty("updatedOn")
    private LocalDateTime updatedOn;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public ErrorLog() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getIdDuplicate() {
        return idDuplicate;
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setIdDuplicate(String idDuplicate) {
        this.idDuplicate = idDuplicate;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}