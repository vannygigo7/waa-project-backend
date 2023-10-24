package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.AuthRequest;
import com.waaproject.waaprojectbackend.dto.response.AuthResponse;
import com.waaproject.waaprojectbackend.model.Role;
import com.waaproject.waaprojectbackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.waaproject.waaprojectbackend.util.JwtTokenUtil;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping({"/auth", "/api/auth"})
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        //authentiate
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            User u = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(u);
            //return token
            String role = u.getRoles().stream().toList().get(0).getName();
            return ResponseEntity.ok(new AuthResponse(u.getEmail(), token, role));
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //failed -> throw unauthorized

    }


}

