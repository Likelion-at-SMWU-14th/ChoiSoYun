package com.likelion.seminar.product.dto;

public record ProductCreateRequest(
        String name,
        int price,
        int stock
) {
}