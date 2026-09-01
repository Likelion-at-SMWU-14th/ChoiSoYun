package com.likelion.seminar.member.dto;

import com.likelion.seminar.member.domain.Member;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String username,
        String email,
        int age,
        boolean active,
        LocalDateTime createdAt
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAge(),
                member.isActive(),
                member.getCreatedAt()
        );
    }
}