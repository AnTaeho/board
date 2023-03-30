package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class ForbiddenWordNotFoundException extends NotFoundException {

    public ForbiddenWordNotFoundException(){
        super(FORBIDDEN_WORD_NOT_FOUND, "해당 단어를 찾을 수 없습니다.");
    }
}
