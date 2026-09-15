package com.likelion.seminar;

import com.likelion.seminar.entity.Author;
import com.likelion.seminar.entity.Comment;
import com.likelion.seminar.entity.Post;
import com.likelion.seminar.repository.AuthorRepository;
import com.likelion.seminar.repository.CommentRepository;
import com.likelion.seminar.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostMappingTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void mappingTest() {

        // 1. 작성자 생성 및 저장
        Author author = new Author("소윤");
        authorRepository.save(author);

        // 2. 게시글 생성 및 작성자 연결
        Post post = new Post(
                "첫 번째 게시글",
                "게시글 내용입니다."
        );

        post.setAuthor(author);

        postRepository.save(post);

        // 3. 댓글 생성 및 게시글, 작성자 연결
        Comment comment = new Comment(
                "첫 번째 댓글입니다."
        );

        comment.setPost(post);
        comment.setAuthor(author);

        commentRepository.save(comment);

        // 4. DB에 실제 반영
        entityManager.flush();

        // 5. 영속성 컨텍스트 초기화
        entityManager.clear();

        // 6. DB에서 다시 조회
        Author findAuthor =
                authorRepository.findById(author.getId())
                        .orElseThrow();

        Post findPost =
                postRepository.findById(post.getId())
                        .orElseThrow();

        Comment findComment =
                commentRepository.findById(comment.getId())
                        .orElseThrow();

        // =========================
        // N -> 1 관계 확인
        // =========================

        // Post -> Author
        assertThat(findPost.getAuthor().getName())
                .isEqualTo("소윤");

        // Comment -> Post
        assertThat(findComment.getPost().getTitle())
                .isEqualTo("첫 번째 게시글");

        // Comment -> Author
        assertThat(findComment.getAuthor().getName())
                .isEqualTo("소윤");


        // =========================
        // 1 -> N 관계 확인
        // =========================

        // Author -> Posts
        assertThat(findAuthor.getPosts())
                .hasSize(1);

        // Author -> Comments
        assertThat(findAuthor.getComments())
                .hasSize(1);

        // Post -> Comments
        assertThat(findPost.getComments())
                .hasSize(1);


        // 실제 데이터 확인
        assertThat(findAuthor.getPosts().get(0).getTitle())
                .isEqualTo("첫 번째 게시글");

        assertThat(findPost.getComments().get(0).getContent())
                .isEqualTo("첫 번째 댓글입니다.");
    }
}