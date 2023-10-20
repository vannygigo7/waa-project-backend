package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("seller")
public class Seller extends User {

}
