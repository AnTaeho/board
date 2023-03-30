package hello.board.controller.forbiddenword.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWordDto {

    @NotEmpty(message = "수정할 단어를 입력해 주세요.")
    private String word;
}
