package hello.board.exception.badrequest;

import hello.board.exception.ErrorCode;

public class AlreadyLoginBadRequest extends BadRequestException{

    public AlreadyLoginBadRequest() {
        super(ErrorCode.ALREADY_LOGIN_BAD_REQUEST, "이미 로그인 되어 있습니다.");
    }
}
