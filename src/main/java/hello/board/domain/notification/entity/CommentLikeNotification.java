package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeNotification extends Notification{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_like_id")
    private CommentLike commentLike;

    private CommentLikeNotification(Member member, Member notifiedMember) {
        inputInfo("comment like", member.getName(), notifiedMember);
    }

    public static CommentLikeNotification from(Member member, Member notifiedMember) {
        return new CommentLikeNotification(member, notifiedMember);
    }

    public void setCommentLike(CommentLike commentLike) {
        this.commentLike = commentLike;
    }
}
