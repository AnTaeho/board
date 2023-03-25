package hello.board.controller.comment.dto.res;

import hello.board.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
public class ChildCommentResDto {

    private Long id;

    private String writer;

    @Lob
    private String Content;

    public ChildCommentResDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.Content = comment.getContent();
    }
}
