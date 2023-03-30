package hello.board.controller.forbiddenword;

import hello.board.controller.forbiddenword.dto.req.AddWordDto;
import hello.board.controller.forbiddenword.dto.req.UpdateWordDto;
import hello.board.controller.forbiddenword.dto.res.WordCacheDto;
import hello.board.controller.forbiddenword.dto.res.WordResDto;
import hello.board.domain.forbiddenword.service.ForbiddenWordCache;
import hello.board.domain.forbiddenword.service.ForbiddenWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden_word")
public class ForbiddenWordController {

    private final ForbiddenWordService forbiddenWordService;

    @GetMapping("/all")
    public List<WordResDto> findAll() {
        return forbiddenWordService.findAll();
    }

    @GetMapping("/{wordId}")
    public WordResDto findOne(@PathVariable final Long wordId) {
        return forbiddenWordService.findOne(wordId);
    }

    @GetMapping("/cache")
    public List<WordCacheDto> findAllCache() {
        return ForbiddenWordCache.getForbiddenWords()
                .stream().map(WordCacheDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public WordResDto save(@Valid @RequestBody final AddWordDto addWordDto) {
        return forbiddenWordService.save(addWordDto);
    }

    @PatchMapping("/{wordId}")
    public void updateWord(@PathVariable final Long wordId,
                           @Valid @RequestBody final UpdateWordDto updateWordDto) {
        forbiddenWordService.updateWord(wordId, updateWordDto);
    }

    @DeleteMapping("/{wordId}")
    public void deleteWord(@PathVariable final Long wordId) {
        forbiddenWordService.deleteWord(wordId);
    }
}
