package com.waaproject.waaprojectbackend.tasks;

import com.waaproject.waaprojectbackend.exception.NoAuctionBiddingException;
import com.waaproject.waaprojectbackend.model.*;
import com.waaproject.waaprojectbackend.service.AuctionService;
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
    private AuctionService auctionService;

    @Scheduled(fixedRate = 10_000) /* Milli seconds */
    @Transactional
    public void myScheduledMethod() {
        for (Product product : productService.findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime.now())) {
            try {
                auctionService.endAuction(product);
            } catch (NoAuctionBiddingException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
