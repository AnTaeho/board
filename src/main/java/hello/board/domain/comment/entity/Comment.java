package hello.board.domain.comment.entity;

import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.CommentNotification;
import hello.board.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String writer;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member commentMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "ownerComment", cascade = CascadeType.REMOVE)
    private List<CommentNotification> notifications = new ArrayList<>();

    public Comment(Member commentMember, String content, Post post) {
        this.writer = commentMember.getName();
        this.commentMember = commentMember;
        this.content = content;
        setPost(post);
    }

    //== 연관관계 메서드 ==//
    private void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    //== 업데이트 로직 ==//
    public void updateInfo(String content) {
        this.content = content;
    }

}
