package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.model.Bid;
import com.waaproject.waaprojectbackend.repository.BidRepository;
import com.waaproject.waaprojectbackend.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid findByHighestBidByAuctionId(Long auctionId) {
        return bidRepository.findByHighestBidByAuctionId(auctionId);
    }
}
