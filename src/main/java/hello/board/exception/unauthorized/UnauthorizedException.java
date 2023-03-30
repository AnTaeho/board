package hello.board.exception.unauthorized;


import hello.board.exception.CustomException;
import hello.board.exception.ErrorCode;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
