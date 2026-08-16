package com.likelion.seminar.repository;

import com.likelion.seminar.entitiy.Post;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Example<? extends Post> Id(Long id);
}
