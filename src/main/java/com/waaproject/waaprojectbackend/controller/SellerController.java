package com.waaproject.waaprojectbackend.controller;

import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.model.Category;
import com.waaproject.waaprojectbackend.service.CategoryService;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/sellers/{sellerId}/products")
@RequestMapping("/api/seller/v1")
@RequiredArgsConstructor
public class SellerController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/products")
    public ProductResponse addNewProduct(@RequestBody ProductRequest productRequest) {
        return productService.addNewProduct(getSellerId(), productRequest);
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProductsBySeller(@RequestParam(required = false) Boolean released) {

        if (released == null) {
            return productService.findProductsBySellerId(getSellerId());
        }

        return productService.findProductsByReleasedAndSellerId(getSellerId(), released);
    }

    @GetMapping("/products/{productId}")
    public ProductResponse getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/products/{productId}")
    public ProductResponse updateUnreleasedProductByIdBySeller(@PathVariable long productId, @RequestBody ProductRequest updatedProductRequest) {
        return productService.updateUnreleasedProductByIdBySeller(getSellerId(), productId, updatedProductRequest);
    }

    @DeleteMapping("/products/{productId}")
    public ProductResponse deleteUnreleasedProductByIdBySeller(@PathVariable long productId) {
        return productService.deleteUnreleasedProductByIdBySeller(getSellerId(), productId);
    }

    @GetMapping("/categories")
    List<Category> findAll() {
        return categoryService.findAll();
    }

    private long getSellerId() {
        return UserContextUtil.getSeller().getId();
    }

}
