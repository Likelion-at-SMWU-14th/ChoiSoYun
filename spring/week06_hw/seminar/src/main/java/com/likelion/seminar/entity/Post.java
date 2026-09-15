package com.likelion.seminar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // 여러 게시글은 한 작성자에게 작성될 수 있음
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // 하나의 게시글에는 여러 댓글이 달릴 수 있음
    // 게시글 삭제 시 댓글도 함께 삭제
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.REMOVE
    )
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}