package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface SellerController {

    ProductResponse addNewProduct(long sellerId, ProductRequest productRequest);

    List<ProductResponse> getAllProductsBySeller(long sellerId, boolean released);

    ProductResponse updateUnreleasedProductByIdBySeller(long sellerId, long productId, ProductRequest updatedProductRequest);

    ProductResponse deleteUnreleasedProductByIdBySeller(long sellerId, long productId);

}
