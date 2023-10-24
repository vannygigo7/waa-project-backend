package com.waaproject.waaprojectbackend.repository;

import com.waaproject.waaprojectbackend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("select b from Bid b where b.auction.id = :auctionId and b.bidAmount = (select max(b2.bidAmount) from Bid b2 where b2.auction.id = :auctionId)")
    Bid findByHighestBidByAuctionId(Long auctionId);

    List<Bid> findByCustomerIdAndAuctionId(long customerId, long auctionId);

}
