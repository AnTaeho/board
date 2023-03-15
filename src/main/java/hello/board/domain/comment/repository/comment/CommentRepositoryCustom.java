package hello.board.domain.comment.repository.comment;

import hello.board.domain.comment.entity.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> findCommentWithMemberInfo(Long commentId);

    Optional<Comment> findCommentWithPostInfo(Long commentId);
}
