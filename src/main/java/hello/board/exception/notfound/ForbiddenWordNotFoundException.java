package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class ForbiddenWordNotFoundException extends NotFoundException {

    public ForbiddenWordNotFoundException(String message){
        super(FORBIDDEN_WORD_NOT_FOUND, message);
    }
}
