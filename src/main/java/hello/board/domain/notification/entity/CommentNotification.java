package hello.board.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Getter
@NoArgsConstructor
public class CommentNotification extends Notification{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment ownerComment;

    private CommentNotification(String writer, Member member, Comment comment) {
        inputInfo(comment.getContent(), writer, member);
        this.ownerComment = comment;
        member.getNotifications().add(this);
    }

    public static CommentNotification from(String writer, Member member, Comment comment) {
        return new CommentNotification(writer, member, comment);
    }

}
