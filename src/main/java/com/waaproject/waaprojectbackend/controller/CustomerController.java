package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.exception.UnauthorizedException;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/customers/{customerId}/products")
@RequestMapping("/api/customer/v1/products")
@RequiredArgsConstructor
public class CustomerController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProductsByCustomer() {
        long customerId = matchTokenWithCustomerId();
        return productService.getAllProductsByCustomer(customerId);
    }

    @PostMapping("/{productId}")
    public ProductResponse bidProduct(@PathVariable long productId, @RequestBody BidRequest bidRequest) {
        long customerId = matchTokenWithCustomerId();
        return productService.bidProduct(customerId, productId, bidRequest);
    }

    private long matchTokenWithCustomerId() {
        Customer customer = UserContextUtil.getCustomer();
        if (customer == null) {
            throw new UnauthorizedException("Valid token required");
        } else {
            return customer.getId();
        }
    }
}
