package com.waaproject.waaprojectbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AuthResponse {

    private long id;
    private String email;
    private String accessToken;
    private String role;
    private String firstName;
    private String lastName;
    private String profileImageUrl;

}
