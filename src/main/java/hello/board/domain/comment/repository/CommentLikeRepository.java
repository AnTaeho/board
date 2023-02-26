package hello.board.domain.comment.repository;

import hello.board.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {
    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);
}
