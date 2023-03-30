package hello.board.exception.unauthorized;

import static hello.board.exception.ErrorCode.*;

public class NotLoginUnauthorizedException extends UnauthorizedException{

    public NotLoginUnauthorizedException(String message) {
        super(NOT_LOGIN_UNAUTHORIZED, message);
    }
}
