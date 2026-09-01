package com.likelion.seminar.member.dto;

import com.likelion.seminar.member.domain.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public record MemberPageResponse(
        List<MemberResponse> content,
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

    public static MemberPageResponse from(Page<Member> page) {
        List<MemberResponse> content = page.getContent()
                .stream()
                .map(MemberResponse::from)
                .toList();

        return new MemberPageResponse(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}