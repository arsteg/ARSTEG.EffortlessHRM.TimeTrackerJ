package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;
import com.timetracker.models.timelog.TimeLog;

public class AddTimeLogAPIResult extends BaseResponse {
    @JsonProperty("data")
    private TimeLog data;

    // Default constructor
    public AddTimeLogAPIResult() {
    }

    // Getter
    public TimeLog getData() {
        return data;
    }

    // Setter
    public void setData(TimeLog data) {
        this.data = data;
    }
}