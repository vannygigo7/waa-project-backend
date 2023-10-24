package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.constant.Role;
import com.waaproject.waaprojectbackend.dto.request.UserRequest;
import com.waaproject.waaprojectbackend.dto.response.UserResponse;
import com.waaproject.waaprojectbackend.exception.DuplicatedUserException;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.model.Wallet;
import com.waaproject.waaprojectbackend.repository.UserRepository;
import com.waaproject.waaprojectbackend.service.RoleService;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.waaproject.waaprojectbackend.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.user.default.wallet.amount}")
    private double defaultWalletAmount;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

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

        try {

            Wallet w2 = new Wallet();
            w2.setBalance(1000);
            w2.setBlockedBalance(0);
            user.setWallet(w2);

            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatedUserException("Duplicated user");
        }

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

        Wallet wallet = new Wallet();
        wallet.setBalance(defaultWalletAmount);
        wallet.setBlockedBalance(0D);
        user.setWallet(wallet);

        user = this.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(role);

        return userResponse;
    }

    @Override
    public List<User> findLosingBidders(long auctionId, long winnerId) {
        return userRepository.findLosingBidders(auctionId, winnerId);
    }

}
