package hello.board.domain.comment.repository.comment;

import hello.board.domain.comment.entity.Comment;
import hello.board.exception.notfound.CommentNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @EntityGraph(attributePaths = {"post", "child"})
    @Query("select c from Comment c where c.id = :commentId")
    Optional<Comment> findWithPostByCommentId(@Param("commentId") Long commentId);

    default Comment findComment(Long commentId) {
        return findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    default Comment findCommentWithPostInfo(Long commentId) {
        return findWithPostByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
