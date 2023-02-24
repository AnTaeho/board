package hello.board.controller.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class CommentReqDto {

    private Long id;

    private String writer;

    @Lob
    private String Content;

}
