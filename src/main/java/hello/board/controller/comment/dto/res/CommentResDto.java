package hello.board.controller.comment.dto.res;

import hello.board.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentResDto {

    private Long id;

    private String writer;

    @Lob
    private String Content;

    private List<ChildCommentResDto> childComment = new ArrayList<>();

    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.Content = comment.getContent();
        List<Comment> child = comment.getChild();
        for (Comment comment1 : child) {
            childComment.add(new ChildCommentResDto(comment1));
        }

    }
}
