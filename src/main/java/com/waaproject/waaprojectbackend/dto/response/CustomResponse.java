package com.waaproject.waaprojectbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomResponse<T> {
//    @JsonProperty("status_code")
    private int statusCode;
    private String message;
    private T data;
}

