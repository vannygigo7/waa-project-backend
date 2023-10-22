package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.exception.NotFoundException;
import com.waaproject.waaprojectbackend.model.Auction;
import com.waaproject.waaprojectbackend.repository.AuctionRepository;
import com.waaproject.waaprojectbackend.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction findById(long id) {
        return auctionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Auction not found")
        );
    }

}
