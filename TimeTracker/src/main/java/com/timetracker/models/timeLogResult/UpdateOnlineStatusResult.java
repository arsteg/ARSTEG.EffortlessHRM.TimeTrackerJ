package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class UpdateOnlineStatusResult extends BaseResponse {
    @JsonProperty("data")
    private UpdateOnlineStatusData data;

    // Default constructor
    public UpdateOnlineStatusResult() {
    }

    // Getter
    public UpdateOnlineStatusData getData() {
        return data;
    }

    // Setter
    public void setData(UpdateOnlineStatusData data) {
        this.data = data;
    }
}