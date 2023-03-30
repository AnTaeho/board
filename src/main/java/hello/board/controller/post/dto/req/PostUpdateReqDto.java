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
public class PostUpdateReqDto {

    @NotEmpty(message = "수정할 제목을 입력해 주세요.")
    private String title;

    @NotEmpty(message = "수정할 내용을 입력해 주세요.")
    @Lob
    private String content;
}
