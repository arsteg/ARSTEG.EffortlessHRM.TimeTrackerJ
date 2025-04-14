package com.timetracker.models.timeLogResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.ErrorLog;
import java.util.List;

public class ErrorLogList {
    @JsonProperty("errorLogList")
    private List<ErrorLog> errorLogList;

    // Default constructor
    public ErrorLogList() {
    }

    // Getter
    public List<ErrorLog> getErrorLogList() {
        return errorLogList;
    }

    // Setter
    public void setErrorLogList(List<ErrorLog> errorLogList) {
        this.errorLogList = errorLogList;
    }
}