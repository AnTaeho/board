package hello.board.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.WRITING;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Post createPost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        return Post.builder()
                .title(postWriteReqDto.getTitle())
                .content(postWriteReqDto.getContent())
                .member(loginMember)
                .build();
    }

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

    public void changeToWaitingPost() {
        this.status = PostStatus.WAITING_TO_POST;
    }

    public void changeToPosted() {
        this.status = PostStatus.POSTED;
    }
}
