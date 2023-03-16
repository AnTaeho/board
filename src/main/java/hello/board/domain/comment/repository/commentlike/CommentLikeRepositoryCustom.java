package hello.board.domain.comment.repository.commentlike;

public interface CommentLikeRepositoryCustom {

    boolean haveNoLike(Long commentId, Long memberId);

    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);

}
