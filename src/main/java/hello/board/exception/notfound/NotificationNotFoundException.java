package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class NotificationNotFoundException extends NotFoundException {

    public NotificationNotFoundException(){
        super(NOTIFICATION_NOT_FOUND, "해당 알람을 찾을 수 없습니다.");
    }
}
