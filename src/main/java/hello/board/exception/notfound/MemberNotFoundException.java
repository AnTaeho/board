package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(){
        super(MEMBER_NOT_FOUND, "해당 회원을 찾을 수 없습니다.");
    }
}
