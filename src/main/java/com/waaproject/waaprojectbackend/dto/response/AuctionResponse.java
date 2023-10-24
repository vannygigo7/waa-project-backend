package com.waaproject.waaprojectbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AuctionResponse {

    private long id;
    private double startPrice;
    private double depositAmount;
    private double highestBid;
    private LocalDateTime bidDueDateTime;
    private LocalDate payDate;
    private Boolean isEnd;

    private List<BidResponse> bids;
}
