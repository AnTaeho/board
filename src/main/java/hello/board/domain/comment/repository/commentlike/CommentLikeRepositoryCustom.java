package hello.board.domain.comment.repository.commentlike;

import hello.board.domain.comment.entity.CommentLike;

public interface CommentLikeRepositoryCustom {

    CommentLike haveNoLike(Long commentId, Long memberId);

}
