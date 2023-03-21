package hello.board.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import lombok.*;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member commentMember;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> child = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(Member commentMember, String content, Post post) {
        this.writer = commentMember.getName();
        this.commentMember = commentMember;
        this.content = content;
        setPost(post);
    }

    public Comment(Member commentMember, String content, Post post, Comment comment) {
        this.writer = commentMember.getName();
        this.commentMember = commentMember;
        this.content = content;
        this.parent = comment;
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
