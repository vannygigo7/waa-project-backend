package com.waaproject.waaprojectbackend.repository;

import com.waaproject.waaprojectbackend.model.AuctionDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionDepositRepository extends JpaRepository<AuctionDeposit, Long> {
    AuctionDeposit findAuctionDepositByAuctionIdAndCustomerId(Long auctionId, Long customerId);
}
