package hello.board.exception.badrequest;

import static hello.board.exception.ErrorCode.*;

public class SelfFollowBadRequestException extends BadRequestException{

    public SelfFollowBadRequestException() {
        super(SELF_FOLLOW_BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다.");
    }
}
