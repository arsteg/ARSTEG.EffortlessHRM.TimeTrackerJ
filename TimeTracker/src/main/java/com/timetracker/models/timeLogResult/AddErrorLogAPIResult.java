package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class AddErrorLogAPIResult extends BaseResponse {
    @JsonProperty("data")
    private AddErrorLog data;

    // Default constructor
    public AddErrorLogAPIResult() {
    }

    // Getter
    public AddErrorLog getData() {
        return data;
    }

    // Setter
    public void setData(AddErrorLog data) {
        this.data = data;
    }
}