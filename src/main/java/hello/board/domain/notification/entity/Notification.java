package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    public Notification(String content,String writer, Member member) {
        this.content = content;
        this.writer = writer;
        this.notifiedMember = member;
        member.getNotifications().add(this);
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
