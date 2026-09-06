package com.likelion.seminar.product.controller;

import com.likelion.seminar.product.dto.ProductResponse;
import com.likelion.seminar.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // [JPA] 가장 비싼 상품 Top10
    @GetMapping("/jpa")
    public List<ProductResponse> findTop10ByJpa() {
        return productService.findTop10ExpensiveProducts();
    }

    // [JPQL] 가격이 2,000원 이하이고 재고가 많은 상품 Top5
    @GetMapping("/jpql")
    public List<ProductResponse> findTop5ByJpql() {
        return productService.findTop5ProductsByJpql();
    }
}