package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface CustomerController {

    List<ProductResponse> getAllProductsByCustomer(long customerId);

    ProductResponse bidProduct(long customerId, long productId, BidRequest bidRequest);

}
