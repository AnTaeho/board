package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(String message){
        super(MEMBER_NOT_FOUND, message);
    }
}
