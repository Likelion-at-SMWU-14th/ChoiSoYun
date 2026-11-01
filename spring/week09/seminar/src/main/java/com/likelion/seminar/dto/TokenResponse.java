package com.likelion.seminar.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}