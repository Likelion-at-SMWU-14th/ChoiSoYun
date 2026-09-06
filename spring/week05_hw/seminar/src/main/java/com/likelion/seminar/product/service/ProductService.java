package com.likelion.seminar.product.service;

import com.likelion.seminar.product.dto.ProductResponse;
import com.likelion.seminar.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // [JPA] 가장 비싼 상품 Top10
    public List<ProductResponse> findTop10ExpensiveProducts() {
        return productRepository.findTop10ByOrderByPriceDesc()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    // [JPQL] 가격이 2,000원 이하이고 재고가 많은 상품 Top5
    public List<ProductResponse> findTop5ProductsByJpql() {

        Pageable pageable = PageRequest.of(0, 5);

        return productRepository
                .findByPriceLessThanEqualOrderByStockDesc(2000, pageable)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}