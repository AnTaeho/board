package hello.board.domain.comment.entity;

import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.CommentLikeNotification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "commentLike", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLikeNotification> notification = new ArrayList<>();

    public CommentLike(Member findMember, Comment findComment) {
        this.member = findMember;
        this.comment = findComment;
    }

    public CommentLike(Member findMember, Comment findComment, CommentLikeNotification notification) {
        this.member = findMember;
        this.comment = findComment;
        this.notification.add(notification);
        notification.setCommentLike(this);
    }
}
