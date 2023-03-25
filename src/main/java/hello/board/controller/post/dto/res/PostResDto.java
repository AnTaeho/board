package hello.board.controller.post.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostResDto {
    private Long id;
    private String title;

    @Lob
    private String content;

    private String memberName;

    private List<CommentResDto> commentResDtos = new ArrayList<>();

    @QueryProjection
    public PostResDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberName = post.getMember().getName();
        for (Comment comment : post.getComments()) {
            commentResDtos.add(new CommentResDto(comment));
        }
    }
}
