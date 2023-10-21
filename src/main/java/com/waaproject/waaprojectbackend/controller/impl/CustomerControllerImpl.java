package com.waaproject.waaprojectbackend.controller.impl;

import com.waaproject.waaprojectbackend.controller.CustomerController;
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
@RequestMapping("/api/v1/customers/{customerId}/products")
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {

    private final ProductService productService;

    @Override
    @GetMapping
    public List<ProductResponse> getAllProductsByCustomer(@PathVariable long customerId) {
        matchTokenWithCustomerId(customerId);
        return productService.getAllProductsByCustomer(customerId);
    }

    @Override
    @PostMapping("/{productId}")
    public ProductResponse bidProduct(@PathVariable long customerId, @PathVariable long productId, @RequestBody BidRequest bidRequest) {
        matchTokenWithCustomerId(customerId);
        return productService.bidProduct(customerId, productId, bidRequest);
    }

    private void matchTokenWithCustomerId(long customerId) {
        Customer customer = UserContextUtil.getCustomer();
        if (customer.getId() != customerId) {
            throw new UnauthorizedException("Invalid customer id " + customerId);
        }
    }
}
