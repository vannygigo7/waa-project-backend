package com.waaproject.waaprojectbackend.controller.impl;

import com.waaproject.waaprojectbackend.constant.Role;
import com.waaproject.waaprojectbackend.controller.ProductController;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.exception.UnauthorizedException;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.model.User;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    @GetMapping
    public List<ProductResponse> getAllAuctioningProducts(@RequestParam(required = false) String name) {
        User user = UserContextUtil.getUser();
        if (user != null) {
            if (user.getRoles().stream().anyMatch(role -> role.getName().equals(Role.USER.getName()))) {
                return productService.getAllAuctioningProducts(name);
            } else {
                return productService.getAllAuctioningProductsNotBySeller(user.getId(), name);
            }
        } else {
            throw new UnauthorizedException("Valid token required");
        }
    }

    @Override
    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }

}
