package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.dto.request.UserRequest;
import com.waaproject.waaprojectbackend.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.waaproject.waaprojectbackend.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(User user);

    UserResponse save(UserRequest userRequest);

    List<User> findLosingBidders(long auctionId, long winnerId);
}
