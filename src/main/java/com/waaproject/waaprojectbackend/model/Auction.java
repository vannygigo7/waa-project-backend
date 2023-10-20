package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double highestPrice;
    private double increasingAmount;
    private LocalDateTime dueDateTime;
    private LocalDate payDate;

    @OneToOne
    private Product product;
    @OneToMany
    private List<Bid> bids;
}
