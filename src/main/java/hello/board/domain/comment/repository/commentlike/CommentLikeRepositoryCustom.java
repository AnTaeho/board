package hello.board.domain.comment.repository.commentlike;

public interface CommentLikeRepositoryCustom {

    boolean hasNoLike(Long commentId, Long memberId);

    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);

}
