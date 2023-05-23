package hello.board.domain.comment.repository.comment;

import hello.board.domain.comment.entity.Comment;

public interface CommentRepositoryCustom {

    Comment findCommentWithMemberInfo(Long commentId);

}
