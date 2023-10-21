package com.waaproject.waaprojectbackend;

import com.waaproject.waaprojectbackend.constant.Profile;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.Role;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.model.User;
import com.waaproject.waaprojectbackend.service.RoleService;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Value("${spring.profiles.active:DEFAULT}")
    private String activeProfile;

    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        if (Profile.DEFAULT.getName().equals(activeProfile)) {
            run();
        }

    }

    private void run() {
        Role userRole = new Role();
        userRole.setName(com.waaproject.waaprojectbackend.constant.Role.USER.getName());
        roleService.save(userRole);

        Role sellerRole = new Role();
        sellerRole.setName(com.waaproject.waaprojectbackend.constant.Role.SELLER.getName());
        roleService.save(sellerRole);

        User user = new Customer();
        user.setEmail("user1@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Set.of(userRole));
        userService.save(user);

        Seller seller = new Seller();
        seller.setEmail("seller1@example.com");
        seller.setPassword(passwordEncoder.encode("password"));
        seller.setRoles(Set.of(sellerRole));
        userService.save(seller);

    }

}
