package com.waaproject.waaprojectbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidResponse {

    private long id;
    @JsonProperty("bid_amount")
    private double bidAmount;

}
