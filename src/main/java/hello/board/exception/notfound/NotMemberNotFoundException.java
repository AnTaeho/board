package hello.board.exception.notfound;

import hello.board.exception.ErrorCode;

public class NotMemberNotFoundException extends NotFoundException{

    public NotMemberNotFoundException() {
        super(ErrorCode.NOT_MEMBER_NOT_FOUND, "해당 정보의 회원을 찾을 수 없습니다.");
    }
}
