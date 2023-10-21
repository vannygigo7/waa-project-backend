package com.waaproject.waaprojectbackend.util;

import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.model.User;
import com.waaproject.waaprojectbackend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class UserContextUtil {

    private static UserService userService;

    @Component
    private static class InnerClass {
        private InnerClass(UserService userService) {
            UserContextUtil.userService = userService;
        }
    }

    public static Customer getCustomer() {
        return (Customer) userService.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static Seller getSeller() {
        return (Seller) userService.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
