package com.waaproject.waaprojectbackend.tasks;

import com.waaproject.waaprojectbackend.model.*;
import com.waaproject.waaprojectbackend.service.BidService;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuctionEndScheduledTask {

    @Autowired
    private ProductService productService;
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 10_000) /* Milli seconds */
    @Transactional
    public void myScheduledMethod() {
        for (Product product : productService.findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime.now())) {

            Auction auction = product.getAuction();
            Bid bid = bidService.findByHighestBidByAuctionId(auction.getId());

            if (bid == null) {
                System.out.println("There is no bid for auction : " + auction.getId());
                continue;
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
        }
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
