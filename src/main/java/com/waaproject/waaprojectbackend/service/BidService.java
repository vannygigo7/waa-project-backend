package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Bid;

public interface BidService {
    Bid findByHighestBidByAuctionId(Long auctionId);
}
