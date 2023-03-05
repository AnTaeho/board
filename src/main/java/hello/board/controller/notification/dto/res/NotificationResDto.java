package hello.board.controller.notification.dto.res;

import hello.board.domain.notification.entity.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResDto {

    private Long id;
    private String content;
    private String writer;

    public NotificationResDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.writer = notification.getWriter();
    }
}
