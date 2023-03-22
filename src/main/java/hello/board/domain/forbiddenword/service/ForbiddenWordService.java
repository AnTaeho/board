package hello.board.domain.forbiddenword.service;

import hello.board.controller.forbiddenword.dto.req.AddWordDto;
import hello.board.controller.forbiddenword.dto.req.UpdateWordDto;
import hello.board.controller.forbiddenword.dto.res.WordDto;
import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.forbiddenword.repository.ForbiddenWordRepository;
import hello.board.exception.CustomNotFoundException;
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
    public WordDto save(AddWordDto addWordDto) {
        ForbiddenWord saveWord = forbiddenWordRepository.save(new ForbiddenWord(addWordDto.getWord()));
        return new WordDto(saveWord);
    }

    public List<WordDto> findAll() {
        return forbiddenWordRepository.findAll()
                .stream().map(WordDto::new)
                .collect(Collectors.toList());
    }

    public WordDto findOne(Long id) {
        return new WordDto(findWord(id));
    }

    @Transactional
    public void updateWord(Long wordId, UpdateWordDto updateWordDto) {
        ForbiddenWord one = findWord(wordId);
        one.updateWord(updateWordDto.getWord());
    }

    @Transactional
    public void deleteWord(Long wordId) {
        forbiddenWordRepository.deleteById(wordId);
    }

    public ForbiddenWord findWord(Long wordId) {
        return forbiddenWordRepository.findById(wordId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",wordId));
                });
    }
}
