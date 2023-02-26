package hello.board.domain.comment.repository;

public interface CommentLikeRepositoryCustom {

    boolean hasNoLike(Long commentId, Long memberId);

}
