package hello.board.controller.forbiddenword.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordCacheDto {

    private String word;

    public WordCacheDto(String word) {
        this.word = word;
    }
}
