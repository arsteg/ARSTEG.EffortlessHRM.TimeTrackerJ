package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class GetErrorLogResult extends BaseResponse {
    @JsonProperty("data")
    private ErrorLogList data;

    // Default constructor
    public GetErrorLogResult() {
    }

    // Getter
    public ErrorLogList getData() {
        return data;
    }

    // Setter
    public void setData(ErrorLogList data) {
        this.data = data;
    }
}