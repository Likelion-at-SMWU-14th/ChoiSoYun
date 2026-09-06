package com.likelion.seminar.product.repository;

import com.likelion.seminar.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.saveAll(List.of(
                new Product("볼펜", 1000, 50),
                new Product("샤프펜슬", 1500, 80),
                new Product("노트", 2000, 30),
                new Product("지우개", 500, 100),
                new Product("형광펜", 1200, 70),
                new Product("만년필", 5000, 10),
                new Product("연필", 800, 60),
                new Product("가위", 2500, 40),
                new Product("테이프", 1800, 90),
                new Product("파일", 2200, 20),
                new Product("스테이플러", 3000, 15),
                new Product("펜케이스", 1900, 55)
        ));
    }

    @Test
    void 가장_비싼_상품_Top10을_조회한다() {
        // when
        List<Product> result =
                productRepository.findTop10ByOrderByPriceDesc();

        // then
        assertThat(result).hasSize(10);

        assertThat(result)
                .extracting(Product::getPrice)
                .containsExactly(
                        5000,
                        3000,
                        2500,
                        2200,
                        2000,
                        1900,
                        1800,
                        1500,
                        1200,
                        1000
                );
    }

    @Test
    void 가격이_2000원_이하이고_재고가_많은_상품_Top5를_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5);

        // when
        List<Product> result =
                productRepository.findByPriceLessThanEqualOrderByStockDesc(
                        2000,
                        pageable
                );

        // then
        assertThat(result).hasSize(5);

        assertThat(result)
                .extracting(Product::getName)
                .containsExactly(
                        "지우개",
                        "테이프",
                        "샤프펜슬",
                        "형광펜",
                        "연필"
                );
    }
}