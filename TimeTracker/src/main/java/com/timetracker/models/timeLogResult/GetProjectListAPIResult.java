package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class GetProjectListAPIResult extends BaseResponse {
    @JsonProperty("data")
    private ProjectList data;

    // Default constructor
    public GetProjectListAPIResult() {
    }

    // Getter
    public ProjectList getData() {
        return data;
    }

    // Setter
    public void setData(ProjectList data) {
        this.data = data;
    }
}