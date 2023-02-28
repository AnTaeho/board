package hello.board.controller.comment.dto.res;

import hello.board.domain.comment.entity.Comment;
import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
