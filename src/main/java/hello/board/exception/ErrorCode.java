package hello.board.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    MEMBER_NOT_FOUND("404_01"),
    POST_NOT_FOUND("404_02"),
    FORBIDDEN_WORD_NOT_FOUND("404_02"),
    NOTIFICATION_NOT_FOUND("404_02"),
    COMMENT_NOT_FOUND("404_03"),
    NOT_MEMBER_NOT_FOUND("404_00"),

    NOT_LOGIN_UNAUTHORIZED("401_01"),

    INVALID_REQUEST_BODY_TYPE("400_01"),
    ALREADY_LOGIN_BAD_REQUEST("400_02"),
    ALREADY_JOIN_BAD_REQUEST("400_03");

    private final String value;

    ErrorCode(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
