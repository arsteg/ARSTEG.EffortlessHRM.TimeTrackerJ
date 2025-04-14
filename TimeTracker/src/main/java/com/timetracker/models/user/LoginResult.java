package com.timetracker.models.user;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResult {
    @JsonProperty("status")
    private String status;

    @JsonProperty("token")
    private String token;

    @JsonProperty("data")
    private LoginresultData data;

    public LoginResult() {
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LoginresultData getData() { return data; }
    public void setData(LoginresultData data) { this.data = data; }
}