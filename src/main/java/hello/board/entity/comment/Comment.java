package hello.board.entity.comment;

import hello.board.entity.post.Post;
import hello.board.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> commentLikeList = new ArrayList<>();

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
