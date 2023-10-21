package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.constant.Role;
import com.waaproject.waaprojectbackend.dto.request.UserRequest;
import com.waaproject.waaprojectbackend.dto.response.UserResponse;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.repository.UserRepository;
import com.waaproject.waaprojectbackend.service.RoleService;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.waaproject.waaprojectbackend.model.User;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByEmail(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        String role = userRequest.getRole();
        boolean isCustomer = Role.USER.getName().equals(role);

        User user = isCustomer ? new Customer() : new Seller();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(
                Set.of(isCustomer ? roleService.findByName(Role.USER.getName()) : roleService.findByName(Role.SELLER.getName()))
        );

        user = this.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(role);

        return userResponse;
    }

}
