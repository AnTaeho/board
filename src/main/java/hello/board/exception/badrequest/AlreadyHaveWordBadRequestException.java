package hello.board.exception.badrequest;

import static hello.board.exception.ErrorCode.*;

public class AlreadyHaveWordBadRequestException extends BadRequestException{

    public AlreadyHaveWordBadRequestException() {
        super(ALREADY_HAVE_WORD_BAD_REQUEST, "이미 금지어 목록에 있습니다.");
    }
}
