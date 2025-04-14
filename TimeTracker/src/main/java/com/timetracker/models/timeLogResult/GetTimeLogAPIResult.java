package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

import java.util.List;

public class GetTimeLogAPIResult extends BaseResponse {
    @JsonProperty("data")
    private List<TimeLogBaseResponse> data;

    // Default constructor
    public GetTimeLogAPIResult() {
    }

    // Getter
    public List<TimeLogBaseResponse> getData() {
        return data;
    }

    // Setter
    public void setData(List<TimeLogBaseResponse> data) {
        this.data = data;
    }
}