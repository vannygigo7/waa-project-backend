package com.waaproject.waaprojectbackend.dto;

import com.waaproject.waaprojectbackend.dto.response.AuctionResponse;
import com.waaproject.waaprojectbackend.dto.response.BidResponse;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.model.Auction;
import com.waaproject.waaprojectbackend.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {

    public static ProductResponse getProductResponse(Product product) {
        List<BidResponse> bidResponses = product.getAuction().getBids().stream()
                .map(bid -> BidResponse.builder()
                        .id(bid.getId())
                        .bidAmount(bid.getBidAmount())
                        .bidDateTime(bid.getBidDateTime())
                        .customerId(bid.getCustomer().getId())
                        .customer(UserDTO.getUserResponse(bid.getCustomer()))
                        .build())
                .toList();
        Auction auction = product.getAuction();
        // if bid due date is before now and product is released, the auction is ended
        boolean isAuctionEnd = auction.getBidDueDateTime().isBefore(LocalDateTime.now())
                && product.isReleased();
        AuctionResponse auctionResponse = AuctionResponse.builder()
                .id(auction.getId())
                .startPrice(auction.getStartPrice())
                .depositAmount(auction.getDepositAmount())
                .highestBid(auction.getHighestBid())
                .bidDueDateTime(auction.getBidDueDateTime())
                .payDate(auction.getPayDate())
                .bids(bidResponses)
                .isEnd(isAuctionEnd)
                .numberOfBidders(auction.getNumberOfBidders())
                .build();
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .categories(product.getCategories())
                .imageUrl(product.getImageUrl())
                .released(product.isReleased())
                .auction(auctionResponse)
                .sellerId(product.getSeller().getId())
                .build();
    }

    public static List<ProductResponse> getProductResponses(List<Product> products) {
        return products.stream()
                .map(product -> getProductResponse(product))
                .toList();
    }

}
