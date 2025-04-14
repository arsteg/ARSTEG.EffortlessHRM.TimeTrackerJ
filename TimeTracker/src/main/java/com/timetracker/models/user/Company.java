package com.timetracker.models.user;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("id")
    private String idDuplicate;

    // Default constructor
    public Company() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIdDuplicate() {
        return idDuplicate;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setIdDuplicate(String idDuplicate) {
        this.idDuplicate = idDuplicate;
    }
}