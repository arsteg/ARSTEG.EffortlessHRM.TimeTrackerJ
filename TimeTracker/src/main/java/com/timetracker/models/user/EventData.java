package com.timetracker.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventData {
    @JsonProperty("EventName")
    private String eventName;

    @JsonProperty("UserId")
    private String userId;

    public EventData() {
    }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}