package hello.board.domain.forbiddenword.service;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.forbiddenword.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForbiddenWordService {

    private final ForbiddenWordRepository forbiddenWordRepository;

    @Transactional
    public ForbiddenWord save(String word) {
        ForbiddenWord forbiddenWord = new ForbiddenWord(word);
        return forbiddenWordRepository.save(forbiddenWord);
    }

    public List<ForbiddenWord> findAll() {
        return forbiddenWordRepository.findAll();
    }

    public ForbiddenWord findOne(Long id) {
        return forbiddenWordRepository.findById(id).get();
    }

    @Transactional
    public void updateWord(Long wordId, String updateWord) {
        ForbiddenWord one = findOne(wordId);
        one.updateWord(updateWord);
    }

    @Transactional
    public void deleteWord(Long wordId) {
        forbiddenWordRepository.deleteById(wordId);
    }
}
