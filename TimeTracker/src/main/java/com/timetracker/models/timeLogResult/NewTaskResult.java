package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class NewTaskResult extends BaseResponse {
    @JsonProperty("data")
    private NewtaskData data;

    // Default constructor
    public NewTaskResult() {
    }

    // Getter
    public NewtaskData getData() {
        return data;
    }

    // Setter
    public void setData(NewtaskData data) {
        this.data = data;
    }
}