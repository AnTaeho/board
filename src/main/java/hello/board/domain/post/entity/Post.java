package hello.board.domain.post.entity;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ownerPost", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //== 업데이트 로직 ==//
    public void updateInfo(PostUpdateReqDto updatePost) {
        this.title = updatePost.getTitle();
        this.content = updatePost.getContent();
    }
}
