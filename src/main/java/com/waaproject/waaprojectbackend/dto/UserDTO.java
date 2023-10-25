package com.waaproject.waaprojectbackend.dto;

import com.waaproject.waaprojectbackend.dto.response.UserResponse;
import com.waaproject.waaprojectbackend.model.Customer;

public class UserDTO {

    public static UserResponse getUserResponse(Customer customer) {
        return UserResponse.builder()
                .email(customer.getEmail())
                .role(customer.getRoles().stream().toList().get(0).getName())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .profileImageUrl(customer.getProfileImageUrl())
                .build();
    }

}
