package hello.board.controller.comment.dto.req;

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
public class CommentWriteDto {

    @NotEmpty(message = "입력할 댓글 내용을 작성해 주세요.")
    @Lob
    private String content;

}
