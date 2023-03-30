package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(){
        super(POST_NOT_FOUND, "해당 게시글을 찾을 수 없습니다.");
    }
}
