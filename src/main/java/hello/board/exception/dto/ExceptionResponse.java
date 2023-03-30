package hello.board.exception.dto;

import hello.board.exception.CustomException;
import hello.board.exception.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private ErrorCode errorCode;

    public ExceptionResponse(Date timestamp, final String message, final ErrorCode errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ExceptionResponse from(final CustomException e) {
        return new ExceptionResponse(new Date(), e.getMessage(), e.getErrorCode());
    }

    public static ExceptionResponse from(final String message, final ErrorCode errorCode) {
        return new ExceptionResponse(new Date(), message, errorCode);
    }
}
