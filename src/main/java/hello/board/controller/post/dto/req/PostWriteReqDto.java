package hello.board.controller.post.dto.req;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class PostWriteReqDto {

    private String title;

    @Lob
    private String content;

    public PostWriteReqDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
