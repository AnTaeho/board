package hello.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Member member;

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        //*중요*//
        this.post = post;
        post.getComments().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.setComment(this);
    }
}
