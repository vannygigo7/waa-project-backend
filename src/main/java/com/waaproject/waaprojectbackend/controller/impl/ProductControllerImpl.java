package com.waaproject.waaprojectbackend.controller.impl;

import com.waaproject.waaprojectbackend.controller.ProductController;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.service.ProductService;
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
        return productService.getAllAuctioningProducts(name);
    }

    @Override
    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }
}
