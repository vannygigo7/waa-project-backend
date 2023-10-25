package com.waaproject.waaprojectbackend.repository;

import com.waaproject.waaprojectbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByReleasedTrueAndTitleContainingIgnoreCaseAndAuctionBidDueDateTimeAfter(String title, LocalDateTime dateTime);

    List<Product> findProductsByReleasedAndAuctionBidDueDateTimeAfter(boolean released, LocalDateTime dateTime);

    List<Product> findProductsByReleasedAndSellerId(boolean released, long id);
    List<Product> findProductsBySellerId(long sellerId);

    List<Product> findProductsByAuctionBidsCustomerId(long id); //might not be possible

    List<Product> findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime bidDueDateTime);

    List<Product> findProductsByReleasedTrueAndSellerIdNotAndTitleContainingIgnoreCase(long id, String title);

    List<Product> findProductsByReleasedTrueAndSellerIdNot(long id);


}
