package hello.board.domain.post.entity;

import hello.board.controller.post.dto.req.PostReqDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.base.BaseTimeEntity;
import hello.board.domain.comment.entity.Comment;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private String title;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(PostReqDto postReqDto) {
        this.title = postReqDto.getTitle();
        this.content = postReqDto.getContent();
    }

    @Lob
    private String content;

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //== 업데이트 로직 ==//
    public void updateInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
