package com.waaproject.waaprojectbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BidRequest {

    @JsonProperty("auction_id")
    private Long auctionId;
    @JsonProperty("bid_amount")
    private Long bidAmount;

}
