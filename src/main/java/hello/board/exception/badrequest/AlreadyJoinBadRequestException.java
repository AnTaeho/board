package hello.board.exception.badrequest;

import static hello.board.exception.ErrorCode.*;

public class AlreadyJoinBadRequestException extends BadRequestException{

    public AlreadyJoinBadRequestException() {
        super(ALREADY_JOIN_BAD_REQUEST, "이미 해당 아이디로 가입했습니다.");
    }
}
