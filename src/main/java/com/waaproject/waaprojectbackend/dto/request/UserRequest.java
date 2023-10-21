package com.waaproject.waaprojectbackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String email;
    private String password;
    private String role;

}
