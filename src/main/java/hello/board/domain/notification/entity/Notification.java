package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
public abstract class Notification {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    protected void inputInfo(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
