package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetOnlineUsersData {
    @JsonProperty("onlineUsers")
    private List<UserDevice> onlineUsers;

    // Default constructor
    public GetOnlineUsersData() {
    }

    // Getter
    public List<UserDevice> getOnlineUsers() {
        return onlineUsers;
    }

    // Setter
    public void setOnlineUsers(List<UserDevice> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}