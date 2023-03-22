package hello.board.controller.forbiddenword;

import hello.board.controller.forbiddenword.dto.req.AddWordDto;
import hello.board.controller.forbiddenword.dto.req.UpdateWordDto;
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
    public WordResDto findOne(@PathVariable Long wordId) {
        return forbiddenWordService.findOne(wordId);
    }

    @GetMapping("/cache")
    public List<WordResDto> findAllCache() {
        return ForbiddenWordCache.getForbiddenWords()
                .stream().map(WordResDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public WordResDto save(@Valid @ModelAttribute AddWordDto addWordDto) {
        return forbiddenWordService.save(addWordDto);
    }

    @PatchMapping("/{wordId}")
    public void updateWord(@PathVariable Long wordId, @Valid @ModelAttribute UpdateWordDto updateWordDto) {
        forbiddenWordService.updateWord(wordId, updateWordDto);
    }

    @DeleteMapping("/{wordId}")
    public void deleteWord(@PathVariable Long wordId) {
        forbiddenWordService.deleteWord(wordId);
    }
}
