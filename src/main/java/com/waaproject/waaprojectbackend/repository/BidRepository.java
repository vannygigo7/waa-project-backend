package com.waaproject.waaprojectbackend.repository;

import com.waaproject.waaprojectbackend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
