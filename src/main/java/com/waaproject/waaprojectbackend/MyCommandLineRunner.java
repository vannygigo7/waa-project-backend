package com.waaproject.waaprojectbackend;

import com.waaproject.waaprojectbackend.constant.Profile;
import com.waaproject.waaprojectbackend.model.*;
import com.waaproject.waaprojectbackend.repository.CategoryRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("activeProfile = " + activeProfile);

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

        Category phone = new Category();
        phone.setName("phone");
        categoryRepository.save(phone);

        Category computer = new Category();
        computer.setName("computer");
        categoryRepository.save(computer);

        Category car = new Category();
        car.setName("car");
        categoryRepository.save(car);

        User customer1 = new Customer();
        customer1.setEmail("customer1@example.com");
        customer1.setPassword(passwordEncoder.encode("password"));
        customer1.setRoles(Set.of(userRole));
        Wallet w1 = new Wallet();
        w1.setBalance(1000);
        w1.setBlockedBalance(0);
        customer1.setWallet(w1);
        userService.save(customer1);

        User customer2 = new Customer();
        customer2.setEmail("customer2@example.com");
        customer2.setPassword(passwordEncoder.encode("password2"));
        customer2.setRoles(Set.of(userRole));
        Wallet w2 = new Wallet();
        w2.setBalance(1000);
        w2.setBlockedBalance(0);
        customer2.setWallet(w2);
        userService.save(customer2);
    }

}
