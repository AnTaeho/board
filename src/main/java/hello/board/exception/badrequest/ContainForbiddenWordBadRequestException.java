package hello.board.exception.badrequest;

import hello.board.exception.ErrorCode;

public class ContainForbiddenWordBadRequestException extends BadRequestException{

    public ContainForbiddenWordBadRequestException() {
        super(ErrorCode.CONTAIN_FORBIDDEN_WORD_BAD_REQUEST, "금지어를 포함하고 있습니다.");
    }
}
