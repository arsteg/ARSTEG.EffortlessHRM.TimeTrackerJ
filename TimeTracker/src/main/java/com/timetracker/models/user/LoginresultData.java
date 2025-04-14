package com.timetracker.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.Login;

public  class LoginresultData {
    @JsonProperty("user")
    private Login user;

    public LoginresultData() {
    }

    public Login getUser() { return user; }
    public void setUser(Login user) { this.user = user; }
}
