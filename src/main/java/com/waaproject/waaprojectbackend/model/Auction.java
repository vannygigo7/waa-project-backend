package com.waaproject.waaprojectbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double startPrice;
    private double depositAmount;
    private double highestBid;

    private LocalDateTime bidDueDateTime;
    private LocalDate payDate;
    private Integer numberOfBidders;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Bid> bids;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Customer winner;
}
