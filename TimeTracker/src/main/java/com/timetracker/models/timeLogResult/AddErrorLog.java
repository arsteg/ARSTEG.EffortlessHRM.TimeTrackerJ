package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.ErrorLog;

public class AddErrorLog {
    @JsonProperty("ErrorLog")
    private ErrorLog errorLog;

    // Default constructor
    public AddErrorLog() {
    }

    // Getter
    public ErrorLog getErrorLog() {
        return errorLog;
    }

    // Setter
    public void setErrorLog(ErrorLog errorLog) {
        this.errorLog = errorLog;
    }
}