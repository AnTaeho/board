package hello.board.domain.comment.repository;

public interface CommentLikeRepositoryCustom {

    boolean hasNoLike(Long commentId, Long memberId);

    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);

}
