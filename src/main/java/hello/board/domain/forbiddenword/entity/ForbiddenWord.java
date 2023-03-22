package hello.board.domain.forbiddenword.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForbiddenWord {

    @Id @GeneratedValue
    private Long id;

    private String word;

    public ForbiddenWord(String word) {
        this.word = word;
    }

    public void updateWord(String updateWord) {
        this.word = updateWord;
    }
}
