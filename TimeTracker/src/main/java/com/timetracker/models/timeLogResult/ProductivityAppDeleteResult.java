package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;
import com.timetracker.models.ProductivityModel;

public class ProductivityAppDeleteResult extends BaseResponse {
    @JsonProperty("data")
    private ProductivityModel data;

    // Default constructor
    public ProductivityAppDeleteResult() {
    }

    // Getter
    public ProductivityModel getData() {
        return data;
    }

    // Setter
    public void setData(ProductivityModel data) {
        this.data = data;
    }
}