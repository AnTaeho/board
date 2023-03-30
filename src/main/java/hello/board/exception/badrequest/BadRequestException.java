package hello.board.exception.badrequest;


import hello.board.exception.CustomException;
import hello.board.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
