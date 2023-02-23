package hello.board.controller.dto;

import hello.board.entity.Comment;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentDto {

    private Long id;

    @Lob
    private String Content;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        Content = comment.getContent();
    }
}
