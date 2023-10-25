package com.waaproject.waaprojectbackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {

    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String profileImageUrl;

}
