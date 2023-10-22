package com.waaproject.waaprojectbackend.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BidRequest {

    private double bidAmount;
    private LocalDateTime bidDateTime;

}
