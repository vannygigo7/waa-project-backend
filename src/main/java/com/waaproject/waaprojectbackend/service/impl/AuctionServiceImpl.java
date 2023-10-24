package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.exception.BadRequestException;
import com.waaproject.waaprojectbackend.exception.NoAuctionBiddingException;
import com.waaproject.waaprojectbackend.model.*;
import com.waaproject.waaprojectbackend.service.AuctionService;
import com.waaproject.waaprojectbackend.service.BidService;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private ProductService productService;
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;

    @Override
    public Product endAuction(Product product) {
        Auction auction = product.getAuction();
        Bid bid = bidService.findByHighestBidByAuctionId(auction.getId());

        if (auction.getWinner() != null) {
            throw new BadRequestException("Auction already has winner");
        }

        if (!product.isReleased()) {
            throw new BadRequestException("You cannot end unreleased auction");
        }

        if (bid == null) {
            throw new NoAuctionBiddingException("There is no bid for auction : " + auction.getId());
        }

        Customer winner = bid.getCustomer();
        Wallet wallet = winner.getWallet();

        double bidAmount      = bid.getBidAmount();
        double depositAmount  = auction.getDepositAmount();
        double blockedBalance = wallet.getBlockedBalance() - depositAmount;
        double balance        = (wallet.getBalance() + depositAmount - bidAmount);

        wallet.setBlockedBalance(blockedBalance);
        wallet.setBalance(balance);
        auction.setWinner(winner);

        // return blocked balance to all loser bidders
        List<User> loserBidders = userService.findLosingBidders(auction.getId(), winner.getId());
        returnBlockedBalanceToLoserBid(loserBidders, auction);

        System.out.printf("product id = " + product.getId());
        System.out.println("bid id = " + bid.getId());

        return product;
    }

    private void returnBlockedBalanceToLoserBid(List<User> customers, Auction auction) {

        for (User customer : customers) {
            Wallet wallet            = customer.getWallet();
            double depositAmount     = auction.getDepositAmount();
            double newBlockedBalance = wallet.getBlockedBalance() - depositAmount;
            double newBalance        = wallet.getBalance() + depositAmount;

            wallet.setBlockedBalance(newBlockedBalance);
            wallet.setBalance(newBalance);
        }

    }

}
