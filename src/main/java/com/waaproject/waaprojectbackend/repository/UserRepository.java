package com.waaproject.waaprojectbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.waaproject.waaprojectbackend.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT b.customer.id FROM Bid b WHERE b.auction.id = :auctionId AND b.customer.id != :winnerId)")
    List<User> findLosingBidders(long auctionId, long winnerId);
}
