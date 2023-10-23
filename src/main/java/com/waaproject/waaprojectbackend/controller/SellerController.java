package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.exception.UnauthorizedException;
import com.waaproject.waaprojectbackend.model.Seller;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/sellers/{sellerId}/products")
@RequestMapping("/api/seller/v1/products")
@RequiredArgsConstructor
public class SellerController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse addNewProduct(@RequestBody ProductRequest productRequest) {
        long sellerId = matchTokenWithSellerId();
        return productService.addNewProduct(sellerId, productRequest);
    }

    @GetMapping
    public List<ProductResponse> getAllProductsBySeller(@RequestParam boolean released) {
        long sellerId = matchTokenWithSellerId();
        return productService.getAllProductsBySeller(sellerId, released);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateUnreleasedProductByIdBySeller(@PathVariable long productId, @RequestBody ProductRequest updatedProductRequest) {
        long sellerId = matchTokenWithSellerId();
        return productService.updateUnreleasedProductByIdBySeller(sellerId, productId, updatedProductRequest);
    }

    @DeleteMapping("/{productId}")
    public ProductResponse deleteUnreleasedProductByIdBySeller(@PathVariable long productId) {
        long sellerId = matchTokenWithSellerId();
        return productService.deleteUnreleasedProductByIdBySeller(sellerId, productId);
    }

    private long matchTokenWithSellerId() {
        Seller seller = UserContextUtil.getSeller();
        if (seller == null) {
            throw new UnauthorizedException("Valid token required");
        } else {
            return seller.getId();
        }
    }
}
