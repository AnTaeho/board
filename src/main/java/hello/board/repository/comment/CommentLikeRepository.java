package hello.board.repository.comment;

import hello.board.entity.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {
    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);
}
