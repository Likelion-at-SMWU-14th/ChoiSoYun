package com.likelion.seminar.product.repository;

import com.likelion.seminar.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // [JPA] 가장 비싼 상품 Top10
    List<Product> findTop10ByOrderByPriceDesc();

    // [JPQL] 가격이 2,000원 이하이고 재고가 많은 상품 Top5
    @Query("""
            SELECT p
            FROM Product p
            WHERE p.price <= :maxPrice
            ORDER BY p.stock DESC
            """)
    List<Product> findByPriceLessThanEqualOrderByStockDesc(
            @Param("maxPrice") int maxPrice,
            Pageable pageable
    );
}