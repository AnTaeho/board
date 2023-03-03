package hello.board.controller.post.dto.req;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteReqDto {

    private String title;

    @Lob
    private String content;
}
