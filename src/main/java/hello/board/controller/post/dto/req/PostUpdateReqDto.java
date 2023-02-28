package hello.board.controller.post.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostUpdateReqDto {

    private String title;

    @Lob
    private String content;
}
