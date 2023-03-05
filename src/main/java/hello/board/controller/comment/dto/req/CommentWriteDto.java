package hello.board.controller.comment.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
public class CommentWriteDto {

    @Lob
    private String content;

}
