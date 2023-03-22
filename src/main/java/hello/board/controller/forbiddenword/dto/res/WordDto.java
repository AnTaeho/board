package hello.board.controller.forbiddenword.dto.res;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordDto {

    private String word;

    public WordDto(ForbiddenWord forbiddenWord) {
        this.word = forbiddenWord.getWord();
    }
}
