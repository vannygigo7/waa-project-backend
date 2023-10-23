package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface SellerController {

    ProductResponse addNewProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProductsBySeller(boolean released);

    ProductResponse updateUnreleasedProductByIdBySeller(long productId, ProductRequest updatedProductRequest);

    ProductResponse deleteUnreleasedProductByIdBySeller(long productId);

}
