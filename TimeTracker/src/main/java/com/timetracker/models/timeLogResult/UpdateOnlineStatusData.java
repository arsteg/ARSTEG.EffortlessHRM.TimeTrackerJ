package com.timetracker.models.timeLogResult;


import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateOnlineStatusData {
    @JsonProperty("userDevice")
    private UserDevice userDevice;

    // Default constructor
    public UpdateOnlineStatusData() {
    }

    // Getter
    public UserDevice getUserDevice() {
        return userDevice;
    }

    // Setter
    public void setUserDevice(UserDevice userDevice) {
        this.userDevice = userDevice;
    }
}