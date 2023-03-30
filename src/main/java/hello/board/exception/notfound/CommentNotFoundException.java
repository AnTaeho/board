package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND, "해당 댓글을 찾을 수 없습니다.");
    }
}
