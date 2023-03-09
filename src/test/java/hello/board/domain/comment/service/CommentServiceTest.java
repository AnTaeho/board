package hello.board.domain.comment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    void findAllComments() {
    }

    @Test
    void findCommentsByPost() {
    }

    @Test
    void writeComment() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void likeComment() {
    }

    @Test
    void findCommentDetail() {
    }
}