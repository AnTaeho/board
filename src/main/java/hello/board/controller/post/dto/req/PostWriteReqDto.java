package hello.board.controller.post.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
public class PostWriteReqDto {

    private String title;

    @Lob
    private String content;
}
