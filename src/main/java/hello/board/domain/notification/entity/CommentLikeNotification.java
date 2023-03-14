package hello.board.domain.notification.entity;

import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CL")
@Getter @Setter
@NoArgsConstructor
public class CommentLikeNotification extends Notification{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_like_id")
    private CommentLike commentLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member notifiedMember;

    public CommentLikeNotification(Member member, Member notifiedMember, Comment comment) {
        inputInfo("comment like", member.getName());
        this.notifiedMember = notifiedMember;
    }
}
