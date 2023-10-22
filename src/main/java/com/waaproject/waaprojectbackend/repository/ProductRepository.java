package com.waaproject.waaprojectbackend.repository;

import com.waaproject.waaprojectbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByTitleContaining(String title);

    List<Product> findProductsByReleased(boolean released);

    List<Product> findProductsByReleasedAndSellerId(boolean released, long id);

    List<Product> findProductsByAuctionBidsCustomerId(long id); //might not be possible

    List<Product> findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime bidDueDateTime);

}
