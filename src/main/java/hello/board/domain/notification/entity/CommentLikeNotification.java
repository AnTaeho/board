package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_like_id")
    private CommentLike commentLike;

    public CommentLikeNotification(Member member, Member notifiedMember) {
        inputInfo("comment like", member.getName(), notifiedMember);
    }
}
