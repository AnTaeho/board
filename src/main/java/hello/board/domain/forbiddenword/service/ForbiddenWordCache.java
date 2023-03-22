package hello.board.domain.forbiddenword.service;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.forbiddenword.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ForbiddenWordCache {

    private final ForbiddenWordRepository forbiddenWordRepository;

    private static List<String> forbiddenWords;

    @PostConstruct
    @Transactional
    public void initForbiddenWordCache() {
        forbiddenWords = forbiddenWordRepository.findAll()
                .stream()
                .map(ForbiddenWord::getWord)
                .collect(Collectors.toList());
    }

    public static List<String> getForbiddenWords() {
        return forbiddenWords;
    }
}
