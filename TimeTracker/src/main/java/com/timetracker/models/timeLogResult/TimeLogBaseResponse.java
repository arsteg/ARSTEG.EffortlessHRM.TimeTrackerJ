package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.user.user;

import java.time.LocalDateTime;

public class TimeLogBaseResponse {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("user")
    private user user;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonProperty("endTime")
    private LocalDateTime endTime;

    @JsonProperty("filePath")
    private String filePath;

    @JsonProperty("keysPressed")
    private int keysPressed;

    @JsonProperty("clicks")
    private int clicks;

    @JsonProperty("url")
    private String url;

    @JsonProperty("scrolls")
    private int scrolls;

    // Default constructor
    public TimeLogBaseResponse() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public user getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getKeysPressed() {
        return keysPressed;
    }

    public int getClicks() {
        return clicks;
    }

    public String getUrl() {
        return url;
    }

    public int getScrolls() {
        return scrolls;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setKeysPressed(int keysPressed) {
        this.keysPressed = keysPressed;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setScrolls(int scrolls) {
        this.scrolls = scrolls;
    }
}