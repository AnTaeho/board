package hello.board.repository.comment;

public interface CommentLikeRepositoryCustom {

    boolean hasNoLike(Long commentId, Long memberId);

}
