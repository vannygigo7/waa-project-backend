package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.exception.BadRequestException;
import com.waaproject.waaprojectbackend.model.Auction;
import com.waaproject.waaprojectbackend.model.AuctionDeposit;
import com.waaproject.waaprojectbackend.model.Bid;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.repository.BidRepository;
import com.waaproject.waaprojectbackend.service.AuctionDepositService;
import com.waaproject.waaprojectbackend.service.AuctionService;
import com.waaproject.waaprojectbackend.service.BidService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionDepositService auctionDepositService;

    @Override
    public Bid createBid(BidRequest request) {

        double bidAmount             = request.getBidAmount();
        Auction auction              = auctionService.findById(request.getAuctionId());
        LocalDateTime bidDueDateTime = auction.getBidDueDateTime();
        double highestPrice          = auction.getHighestPrice();
        double startPrice            = auction.getStartPrice();
        double depositAmount         = auction.getDepositAmount();
        Customer customer            = UserContextUtil.getCustomer();
        LocalDateTime now            = LocalDateTime.now();

        if (bidAmount <= startPrice) {
            throw new BadRequestException("Bid amount should be greater than the auction starting price");
        }

        if (bidAmount <= highestPrice) {
            throw new BadRequestException("Bid amount should be greater than the auction highest bidding price");
        }

        if (now.isAfter(bidDueDateTime)) {
            throw new BadRequestException("Auction was closed");
        }

        AuctionDeposit auctionDeposit = auctionDepositService.findAuctionDepositByAuctionIdAndCustomerId(auction.getId(), customer.getId());
        if (auctionDeposit == null) {
            auctionDeposit = AuctionDeposit.builder()
                    .auction(auction)
                    .customer(customer)
                    .build();
        }

        auctionDeposit.setDepositAmount(depositAmount);
        auctionDepositService.save(auctionDeposit);

        Bid bid = Bid.builder()
                .bidAmount(request.getBidAmount())
                .bidDateTime(now)
                .auction(auction)
                .customer(customer)
                .build();

        return bidRepository.save(bid);
    }

}
