package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.comment.entity.Comment;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment ownerComment;

    public Notification(String content,String writer, Member member, Comment comment) {
        this.content = content;
        this.writer = writer;
        this.notifiedMember = member;
        this.ownerComment = comment;
        member.getNotifications().add(this);
        comment.getNotifications().add(this);
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
