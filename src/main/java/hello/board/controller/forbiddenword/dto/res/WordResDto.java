package hello.board.controller.forbiddenword.dto.res;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordResDto {

    private Long id;

    private String word;

    public WordResDto(ForbiddenWord forbiddenWord) {
        this.id = forbiddenWord.getId();
        this.word = forbiddenWord.getWord();
    }
}
