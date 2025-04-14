package com.timetracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ProductivityModel {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("isApproved")
    private Boolean isApproved;

    @JsonProperty("CreatedOn")
    private LocalDateTime createdOn;

    @JsonProperty("UpdatedOn")
    private LocalDateTime updatedOn;

    @JsonProperty("CreatedBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("company")
    private String company;

    // Default constructor
    public ProductivityModel() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getCompany() {
        return company;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}