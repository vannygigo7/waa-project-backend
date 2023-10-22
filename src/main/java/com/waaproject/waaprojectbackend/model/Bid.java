package com.waaproject.waaprojectbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double bidAmount;
    private LocalDateTime bidDateTime;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JsonBackReference
    private Auction auction;
}
