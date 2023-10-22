package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface CustomerController {

    List<ProductResponse> getAllProductsByCustomer();

    ProductResponse bidProduct(long productId, BidRequest bidRequest);

}
