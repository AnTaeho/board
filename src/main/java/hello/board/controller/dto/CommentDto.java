package hello.board.controller.dto;

import hello.board.entity.Comment;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentDto {

    private Long id;

    private String writer;

    @Lob
    private String Content;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.Content = comment.getContent();
    }
}
