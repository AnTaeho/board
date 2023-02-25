package hello.board.controller.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentReqDto {

    @Lob
    private String Content;

}
