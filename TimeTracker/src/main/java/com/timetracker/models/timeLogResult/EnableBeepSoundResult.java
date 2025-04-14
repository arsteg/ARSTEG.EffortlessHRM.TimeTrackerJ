package com.timetracker.models.timeLogResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

public class EnableBeepSoundResult extends BaseResponse {
    @JsonProperty("data")
    private boolean data;

    // Default constructor
    public EnableBeepSoundResult() {
    }

    // Getter
    public boolean isData() {
        return data;
    }

    // Setter
    public void setData(boolean data) {
        this.data = data;
    }
}
