package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addNewProduct(long sellerId, ProductRequest productRequest);

    List<ProductResponse> getAllAuctioningProducts(String name);

    List<ProductResponse> getAllProductsBySeller(long sellerId, boolean released);

    List<ProductResponse> getAllProductsByCustomer(long customerId);

//    List<ProductResponse> getAllUnreleasedProductsBySeller(long sellerId);

    ProductResponse getProductById(long productId);

    ProductResponse updateUnreleasedProductByIdBySeller(long sellerId, long productId, ProductRequest updatedProductRequest);

    ProductResponse deleteUnreleasedProductByIdBySeller(long sellerId, long productId);

    ProductResponse bidProduct(long customerId, long productId, BidRequest bidRequest);
}
