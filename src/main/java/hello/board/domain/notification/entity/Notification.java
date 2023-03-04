package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_like_id")
    private CommentLike commentLike;


    public Notification(String content, String writer, Member member, Comment comment) {
        this.content = content;
        this.writer = writer;
        this.notifiedMember = member;
        this.ownerComment = comment;
        member.getNotifications().add(this);
        comment.getNotifications().add(this);
    }

    public Notification(Member member, Comment comment, Member notifiedMember) {
        this.content = "comment like";
        this.writer = member.getName();
        this.ownerComment = comment;
        this.notifiedMember = notifiedMember;
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
