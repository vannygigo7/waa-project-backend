package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.model.AuctionDeposit;
import com.waaproject.waaprojectbackend.repository.AuctionDepositRepository;
import com.waaproject.waaprojectbackend.service.AuctionDepositService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuctionDepositServiceImpl implements AuctionDepositService {

    private final AuctionDepositRepository auctionDepositRepository;

    @Override
    public AuctionDeposit save(AuctionDeposit auctionDeposit) {
        return auctionDepositRepository.save(auctionDeposit);
    }

    @Override
    public AuctionDeposit findAuctionDepositByAuctionIdAndCustomerId(Long auctionId, Long customerId) {
        return auctionDepositRepository.findAuctionDepositByAuctionIdAndCustomerId(auctionId, customerId);
    }

}
