package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Bid;

import java.util.List;

public interface BidService {
    Bid findByHighestBidByAuctionId(Long auctionId);

    List<Bid> findByCustomerIdAndAuctionId(long customerId, long auctionId);
}
