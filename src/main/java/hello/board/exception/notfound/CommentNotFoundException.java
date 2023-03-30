package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(String message) {
        super(COMMENT_NOT_FOUND, message);
    }
}
