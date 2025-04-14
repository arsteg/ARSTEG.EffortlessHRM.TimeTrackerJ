package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class GetOnlineUsersByCompanyResult extends BaseResponse {
    @JsonProperty("data")
    private GetOnlineUsersData data;

    // Default constructor
    public GetOnlineUsersByCompanyResult() {
    }

    // Getter
    public GetOnlineUsersData getData() {
        return data;
    }

    // Setter
    public void setData(GetOnlineUsersData data) {
        this.data = data;
    }
}