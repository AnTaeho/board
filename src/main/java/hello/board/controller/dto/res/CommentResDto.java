package hello.board.controller.dto.res;

import hello.board.entity.comment.Comment;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentResDto {

    private Long id;

    private String writer;

    @Lob
    private String Content;

    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.Content = comment.getContent();
    }
}
