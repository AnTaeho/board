package hello.board.exception.unauthorized;

import static hello.board.exception.ErrorCode.DELETE_UNAUTHORIZED;

public class DeleteUnauthorizedException extends UnauthorizedException{

    public DeleteUnauthorizedException() {
        super(DELETE_UNAUTHORIZED, "삭제할 권한이 없습니다.");
    }
}
