package com.waaproject.waaprojectbackend.dto;

import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.model.Product;

import java.util.List;

public class ProductDTO {

    public static ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .categories(product.getCategories())
                .released(product.isReleased())
                .auction(product.getAuction())
                .sellerId(product.getSeller().getId())
                .build();
    }

    public static List<ProductResponse> getProductResponses(List<Product> products) {
        return products.stream()
                .map(product -> getProductResponse(product))
                .toList();
    }

}
