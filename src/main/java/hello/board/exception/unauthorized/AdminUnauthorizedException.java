package hello.board.exception.unauthorized;

import static hello.board.exception.ErrorCode.ADMIN_UNAUTHORIZED;

public class AdminUnauthorizedException extends UnauthorizedException{

    public AdminUnauthorizedException() {
        super(ADMIN_UNAUTHORIZED, "관리자 권한이 없습니다.");
    }
}
