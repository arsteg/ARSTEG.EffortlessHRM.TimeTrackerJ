package com.timetracker.models.timelog;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class CreateTaskRequest {
    @JsonProperty("taskName")
    private String taskName;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonProperty("description")
    private String description;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("project")
    private String project;

    @JsonProperty("taskUsers")
    private String[] taskUsers;

    @JsonProperty("user")
    private String user;

    @JsonProperty("taskAttachments")
    private List<TaskAttachment> taskAttachments;

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public CreateTaskRequest() {
    }

    // Getters
    public String getTaskName() {
        return taskName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public String getPriority() {
        return priority;
    }

    public String getProject() {
        return project;
    }

    public String[] getTaskUsers() {
        return taskUsers;
    }

    public String getUser() {
        return user;
    }

    public List<TaskAttachment> getTaskAttachments() {
        return taskAttachments;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setTaskUsers(String[] taskUsers) {
        this.taskUsers = taskUsers;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTaskAttachments(List<TaskAttachment> taskAttachments) {
        this.taskAttachments = taskAttachments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}