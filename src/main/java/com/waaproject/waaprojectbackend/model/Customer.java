package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User{

    @ManyToMany
    private List<Auction> auctions;
}
