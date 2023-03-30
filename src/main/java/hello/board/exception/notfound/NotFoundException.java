package hello.board.exception.notfound;


import hello.board.exception.CustomException;
import hello.board.exception.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
