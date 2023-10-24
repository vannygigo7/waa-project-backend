package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.constant.Role;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.exception.UnauthorizedException;
import com.waaproject.waaprojectbackend.model.User;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllAuctioningProducts(@RequestParam(required = false) String name) {
        User user = UserContextUtil.getUser();
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(Role.USER.getName()))) {
            return productService.getAllAuctioningProducts(name);
        } else {
            return productService.getAllAuctioningProductsNotBySeller(user.getId(), name);
        }
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }

}
