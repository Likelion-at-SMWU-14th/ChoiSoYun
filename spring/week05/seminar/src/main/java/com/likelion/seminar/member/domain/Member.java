package com.likelion.seminar.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private int age;

    private boolean active;

    private LocalDateTime createdAt;

    // JPA가 사용하는 기본 생성자
    protected Member() {
    }

    private Member(
            String username,
            String email,
            int age
    ) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    // 회원 생성용 정적 팩토리 메서드
    public static Member create(
            String username,
            String email,
            int age
    ) {
        return new Member(username, email, age);
    }

    // 회원 나이 변경
    public void changeAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}