package hello.board.exception.unauthorized;

import static hello.board.exception.ErrorCode.*;

public class UpdateUnauthorizedException extends UnauthorizedException{

    public UpdateUnauthorizedException() {
        super(UPDATE_UNAUTHORIZED, "수정할 권한이 없습니다.");
    }
}
