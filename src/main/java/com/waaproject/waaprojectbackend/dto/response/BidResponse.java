package com.waaproject.waaprojectbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BidResponse {

    private long id;
    private double bidAmount;
    private LocalDateTime bidDateTime;

    private long customerId;
}
