package com.waaproject.waaprojectbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAuctionBiddingException extends RuntimeException {

    public NoAuctionBiddingException(String msg) {
        super(msg);
    }

}
