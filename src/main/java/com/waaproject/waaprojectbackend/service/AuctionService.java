package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Auction;

public interface AuctionService {
    Auction save(Auction auction);

    Auction findById(long id);
}
