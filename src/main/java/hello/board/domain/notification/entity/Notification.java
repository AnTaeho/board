package hello.board.domain.notification.entity;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "post_id")
    private Post ownerPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment ownerComment;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Notification(Member member, Member notifiedMember, Comment comment) {
        this.content = "comment like";
        this.writer = member.getName();
        this.ownerComment = comment;
        this.notifiedMember = notifiedMember;
    }

    public Notification(String writer, Member loginMember, Post post) {
        this.content = post.getTitle();
        this.writer = writer;
        this.notifiedMember = loginMember;
        this.ownerPost = post;
    }

    public void updateInfo(NotificationUpdateReqDto updateReqDto) {
        this.content = updateReqDto.getContent();
    }
}
