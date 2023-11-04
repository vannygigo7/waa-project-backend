package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.UserRequest;
import com.waaproject.waaprojectbackend.dto.response.UserResponse;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/users", "/api/auth"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping({"","/register"})
    public UserResponse users(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

}
