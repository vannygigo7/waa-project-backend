package com.waaproject.waaprojectbackend.controller.customer;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.response.BidResponse;
import com.waaproject.waaprojectbackend.model.Bid;
import com.waaproject.waaprojectbackend.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/bidding")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping
    public BidResponse createBidding(@RequestBody BidRequest request) {
        Bid bid = bidService.createBid(request);
        return BidResponse.builder()
                .id(bid.getId())
                .bidAmount(bid.getBidAmount())
                .build();
    }

}
