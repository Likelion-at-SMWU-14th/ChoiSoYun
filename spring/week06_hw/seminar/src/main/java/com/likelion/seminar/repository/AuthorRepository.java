package com.likelion.seminar.repository;

import com.likelion.seminar.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}