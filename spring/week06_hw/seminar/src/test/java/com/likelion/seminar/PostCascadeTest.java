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
public class PostCascadeTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void cascadeRemoveTest() {

        // 1. 작성자 저장
        Author author = new Author("소윤");
        authorRepository.save(author);

        // 2. 게시글 저장
        Post post = new Post(
                "삭제 테스트 게시글",
                "게시글 내용"
        );

        post.setAuthor(author);
        postRepository.save(post);

        // 3. 댓글 저장
        Comment comment = new Comment(
                "삭제 테스트 댓글"
        );

        comment.setAuthor(author);
        comment.setPost(post);

        commentRepository.save(comment);

        Long postId = post.getId();
        Long commentId = comment.getId();

        // 4. DB에 반영
        entityManager.flush();
        entityManager.clear();

        // 댓글이 존재하는지 먼저 확인
        assertThat(commentRepository.findById(commentId))
                .isPresent();

        // 5. 게시글 삭제
        Post findPost = postRepository.findById(postId)
                .orElseThrow();

        postRepository.delete(findPost);

        entityManager.flush();
        entityManager.clear();

        // 6. 게시글 삭제 확인
        assertThat(postRepository.findById(postId))
                .isEmpty();

        // 7. 댓글도 같이 삭제됐는지 확인
        assertThat(commentRepository.findById(commentId))
                .isEmpty();
    }
}