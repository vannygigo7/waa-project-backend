package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.model.Bid;

public interface BidService {
    Bid createBid(BidRequest request);
}
