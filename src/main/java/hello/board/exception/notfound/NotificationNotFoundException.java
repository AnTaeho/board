package hello.board.exception.notfound;

import static hello.board.exception.ErrorCode.*;

public class NotificationNotFoundException extends NotFoundException {

    public NotificationNotFoundException(String message){
        super(NOTIFICATION_NOT_FOUND, message);
    }
}
