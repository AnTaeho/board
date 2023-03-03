package hello.board.controller.notification.dto.res;

import hello.board.domain.notification.entity.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationUpdateResDto {

    private Long id;
    private String content;
    private String writer;

    public NotificationUpdateResDto(Notification updateNotification) {
        this.id = updateNotification.getId();
        this.content = updateNotification.getContent();
        this.writer = updateNotification.getWriter();
    }
}
