package hello.board.entity;

import hello.board.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String writer;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        //*중요*//
        this.post = post;
        post.getComments().add(this);
    }

}
