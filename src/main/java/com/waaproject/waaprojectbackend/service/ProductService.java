package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    ProductResponse addNewProduct(long sellerId, ProductRequest productRequest);

    List<ProductResponse> getAllAuctioningProducts(String name);

    List<ProductResponse> getAllAuctioningProductsNotBySeller(long sellerId, String name);

    List<ProductResponse> findProductsBySellerId(long sellerId);

    List<ProductResponse> findProductsByReleasedAndSellerId(long sellerId, boolean released);

    List<ProductResponse> getAllProductsByCustomer(long customerId);

//    List<ProductResponse> getAllUnreleasedProductsBySeller(long sellerId);

    ProductResponse getProductById(long productId);

    ProductResponse updateUnreleasedProductByIdBySeller(long sellerId, long productId, ProductRequest updatedProductRequest);

    ProductResponse deleteUnreleasedProductByIdBySeller(long sellerId, long productId);

    ProductResponse bidProduct(long customerId, long productId, BidRequest bidRequest);

    List<Product> findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime bidDueDateTime);
}
