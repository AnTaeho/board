package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(String message){
        super(POST_NOT_FOUND, message);
    }
}
