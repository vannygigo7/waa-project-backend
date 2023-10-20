package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double balance;
//    1000 - 100 = 900; 900 -200 = 700
    private double blockBalance;
//    100 + 200

    @OneToOne
    private User user;
}


