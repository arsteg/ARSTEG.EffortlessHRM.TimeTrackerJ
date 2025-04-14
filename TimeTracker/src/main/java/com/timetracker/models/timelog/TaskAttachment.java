package com.timetracker.models.timelog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAttachment {
    @JsonProperty("attachmentType")
    private String attachmentType;

    @JsonProperty("attachmentName")
    private String attachmentName;

    @JsonProperty("attachmentSize")
    private String attachmentSize;

    @JsonProperty("file")
    private String file;

    // Default constructor
    public TaskAttachment() {
    }

    // Getters
    public String getAttachmentType() {
        return attachmentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getAttachmentSize() {
        return attachmentSize;
    }

    public String getFile() {
        return file;
    }

    // Setters
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public void setAttachmentSize(String attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    public void setFile(String file) {
        this.file = file;
    }
}