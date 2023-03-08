package hello.board.controller.post.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteReqDto {

    @NotEmpty
    private String title;

    @NotEmpty
    @Lob
    private String content;
}
