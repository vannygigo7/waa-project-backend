package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.AuctionDeposit;

public interface AuctionDepositService {
    AuctionDeposit save(AuctionDeposit auctionDeposit);

    AuctionDeposit findAuctionDepositByAuctionIdAndCustomerId(Long auctionId, Long customerId);
}
