package hello.board.controller.comment.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentWriteDto {

    @Lob
    private String Content;

}
