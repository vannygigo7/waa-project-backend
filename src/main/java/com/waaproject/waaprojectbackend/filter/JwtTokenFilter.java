package com.waaproject.waaprojectbackend.filter;

import com.waaproject.waaprojectbackend.model.Role;
import com.waaproject.waaprojectbackend.model.User;
import com.waaproject.waaprojectbackend.service.UserService;
import com.waaproject.waaprojectbackend.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        System.out.println("Authorization = " + authHeader);

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split(" ")[1];
        boolean result = tokenUtil.validateToken(token);
        if (!result){
            filterChain.doFilter(request, response);
            return;
        }

        //get Authentication, save SecurityContext
//        1. parseClaim
        Claims claims = tokenUtil.parseClaims(token);
        String email = claims.getSubject();

        User user = userService.findByEmail(email);
        List<SimpleGrantedAuthority> list = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
                .toList();

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, list);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

}

