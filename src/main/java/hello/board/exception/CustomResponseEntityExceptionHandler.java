package hello.board.exception;

import hello.board.exception.badrequest.BadRequestException;
import hello.board.exception.dto.ExceptionResponse;
import hello.board.exception.notfound.NotFoundException;
import hello.board.exception.unauthorized.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static hello.board.exception.ErrorCode.INVALID_REQUEST_BODY_TYPE;

@Slf4j
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(NotFoundException e){
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(BadRequestException e){
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(UnauthorizedException e){
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(final MethodArgumentNotValidException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), INVALID_REQUEST_BODY_TYPE.getValue(), e.getMessage());
        final StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> stringBuilder.append(error.getDefaultMessage())
                .append(System.lineSeparator()));
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.from(stringBuilder.toString(), INVALID_REQUEST_BODY_TYPE));
    }
}
