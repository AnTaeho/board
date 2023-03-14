package hello.board.domain.notification.entity;

import hello.board.domain.comment.entity.Comment;
import hello.board.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Getter @Setter
@NoArgsConstructor
public class CommentNotification extends Notification{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment ownerComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    public CommentNotification(String content, String writer, Member member, Comment comment) {
        inputInfo(content, writer);
        this.notifiedMember = member;
        this.ownerComment = comment;
        member.getNotifications().add(this);
        comment.getNotifications().add(this);
    }

}
