package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface ProductController {

    List<ProductResponse> getAllAuctioningProducts(String name);

    ProductResponse getProductById(long productId);

}
