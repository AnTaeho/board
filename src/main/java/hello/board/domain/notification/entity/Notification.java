package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Notification {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;
    private String writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    protected void inputInfo(String content, String writer, Member member) {
        this.content = content;
        this.writer = writer;
        this.notifiedMember = member;
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
