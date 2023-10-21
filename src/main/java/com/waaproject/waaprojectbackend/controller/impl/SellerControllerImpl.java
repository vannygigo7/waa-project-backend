package com.waaproject.waaprojectbackend.controller.impl;

import com.waaproject.waaprojectbackend.controller.SellerController;
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
@RequestMapping("/api/v1/sellers/{sellerId}/products")
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {

    private final ProductService productService;

    @Override
    @PostMapping
    public ProductResponse addNewProduct(@PathVariable long sellerId, @RequestBody ProductRequest productRequest) {
        matchTokenWithSellerId(sellerId);
        return productService.addNewProduct(sellerId, productRequest);
    }

    @Override
    @GetMapping
    public List<ProductResponse> getAllProductsBySeller(@PathVariable long sellerId, @RequestParam boolean released) {
        matchTokenWithSellerId(sellerId);
        return productService.getAllProductsBySeller(sellerId, released);
    }

    @Override
    @PutMapping("/{productId}")
    public ProductResponse updateUnreleasedProductByIdBySeller(@PathVariable long sellerId, @PathVariable long productId, @RequestBody ProductRequest updatedProductRequest) {
        matchTokenWithSellerId(sellerId);
        return productService.updateUnreleasedProductByIdBySeller(sellerId, productId, updatedProductRequest);
    }

    @Override
    @DeleteMapping("/{productId}")
    public ProductResponse deleteUnreleasedProductByIdBySeller(@PathVariable long sellerId, @PathVariable long productId) {
        matchTokenWithSellerId(sellerId);
        return productService.deleteUnreleasedProductByIdBySeller(sellerId, productId);
    }

    private void matchTokenWithSellerId(long sellerId) {
        Seller seller = UserContextUtil.getSeller();
        if (seller.getId() != sellerId) {
            throw new UnauthorizedException("Invalid seller id " + sellerId);
        }
    }
}
