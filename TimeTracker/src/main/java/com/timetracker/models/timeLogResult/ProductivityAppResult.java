package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;
import com.timetracker.models.ProductivityModel;

import java.util.List;

public class ProductivityAppResult extends BaseResponse {
    @JsonProperty("data")
    private List<ProductivityModel> data;

    // Default constructor
    public ProductivityAppResult() {
    }

    // Getter
    public List<ProductivityModel> getData() {
        return data;
    }

    // Setter
    public void setData(List<ProductivityModel> data) {
        this.data = data;
    }
}