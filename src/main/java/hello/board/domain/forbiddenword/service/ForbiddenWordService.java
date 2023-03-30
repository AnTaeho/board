package hello.board.domain.forbiddenword.service;

import hello.board.controller.forbiddenword.dto.req.AddWordDto;
import hello.board.controller.forbiddenword.dto.req.UpdateWordDto;
import hello.board.controller.forbiddenword.dto.res.WordResDto;
import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.forbiddenword.repository.ForbiddenWordRepository;
import hello.board.exception.notfound.ForbiddenWordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForbiddenWordService {

    private final ForbiddenWordRepository forbiddenWordRepository;

    @Transactional
    public WordResDto save(final AddWordDto addWordDto) {
        ForbiddenWord saveWord = forbiddenWordRepository.save(ForbiddenWord.from(addWordDto.getWord()));
        ForbiddenWordCache.addForbiddenWord(saveWord);
        return new WordResDto(saveWord);
    }

    public List<WordResDto> findAll() {
        return forbiddenWordRepository.findAll()
                .stream().map(WordResDto::new)
                .collect(Collectors.toList());
    }

    public WordResDto findOne(final Long wordId) {
        return new WordResDto(findWord(wordId));
    }

    @Transactional
    public void updateWord(final Long wordId, final UpdateWordDto updateWordDto) {
        ForbiddenWord findWord = findWord(wordId);
        findWord.updateWord(updateWordDto.getWord());
    }

    @Transactional
    public void deleteWord(final Long wordId) {
        forbiddenWordRepository.deleteById(wordId);
    }

    public ForbiddenWord findWord(final Long wordId) {
        return forbiddenWordRepository.findById(wordId)
                .orElseThrow(() -> {
                    throw new ForbiddenWordNotFoundException(String.format("id=%s not found",wordId));
                });
    }
}
