package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;
    private String writer;

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
