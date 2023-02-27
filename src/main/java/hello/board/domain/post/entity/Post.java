package hello.board.domain.post.entity;

import hello.board.controller.post.dto.req.PostReqDto;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post from(PostReqDto postReqDto) {
        return new Post(postReqDto.getTitle(), postReqDto.getContent());
    }

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //== 업데이트 로직 ==//
    public void updateInfo(Post updatePost) {
        this.title = updatePost.getTitle();
        this.content = updatePost.getContent();
    }
}
