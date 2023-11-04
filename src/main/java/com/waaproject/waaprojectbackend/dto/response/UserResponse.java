package com.waaproject.waaprojectbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String profileImageUrl;

}
