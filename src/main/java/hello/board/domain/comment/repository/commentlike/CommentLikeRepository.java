package hello.board.domain.comment.repository.commentlike;

import hello.board.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    CommentLike findByCommentIdAndMemberId(Long commentId, Long memberId);
}
