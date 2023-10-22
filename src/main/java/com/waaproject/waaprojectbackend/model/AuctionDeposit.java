package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double depositAmount;

    @ManyToOne
    private Auction auction;
    @ManyToOne
    private Customer customer;

}
