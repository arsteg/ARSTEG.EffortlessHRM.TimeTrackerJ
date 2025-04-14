package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDevice {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("machineId")
    private String machineId;

    @JsonProperty("isOnline")
    private boolean isOnline;

    @JsonProperty("company")
    private String company;

    @JsonProperty("_id")
    private String id;

    // Default constructor
    public UserDevice() {
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getCompany() {
        return company;
    }

    public String getId() {
        return id;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setId(String id) {
        this.id = id;
    }
}