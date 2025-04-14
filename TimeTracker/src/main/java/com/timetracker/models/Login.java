package com.timetracker.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.user.Company;

public class Login {
    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("id")
    private String id;

    @JsonProperty("company")
    private Company company;

    // Default constructor
    public Login() {
    }

    // Constructor for minimal login (email and password)
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

