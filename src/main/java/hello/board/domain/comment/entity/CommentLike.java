package hello.board.domain.comment.entity;

import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.CommentLikeNotification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private CommentLike(Member findMember, Comment findComment) {
        this.member = findMember;
        this.comment = findComment;
    }

    public static CommentLike makeCommentLike(Member loginMember, Comment findComment) {
        return new CommentLike(loginMember, findComment);
    }

    public static CommentLike makeCommentLikeWithNotification(Member loginMember, Comment findComment, CommentLikeNotification notification) {
        CommentLike commentLike = new CommentLike(loginMember, findComment);
        notification.setCommentLike(commentLike);
        return commentLike;
    }
}
