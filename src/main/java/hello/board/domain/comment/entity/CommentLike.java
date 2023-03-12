package hello.board.domain.comment.entity;

import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.Notification;
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

    @OneToOne(mappedBy = "commentLike", cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;

    public CommentLike(Member findMember, Comment findComment) {
        this.member = findMember;
        this.comment = findComment;
    }

    public CommentLike(Member findMember, Comment findComment, Notification notification) {
        this.member = findMember;
        this.comment = findComment;
        this.notification = notification;
        notification.setCommentLike(this);
    }
}
