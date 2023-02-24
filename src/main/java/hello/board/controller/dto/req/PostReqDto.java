package hello.board.controller.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostReqDto {

    private Long id;
    private String title;

    @Lob
    private String content;

}
