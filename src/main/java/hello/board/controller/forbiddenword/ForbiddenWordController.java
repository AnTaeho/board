package hello.board.controller.forbiddenword;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.forbiddenword.service.ForbiddenWordCache;
import hello.board.domain.forbiddenword.service.ForbiddenWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden_word")
public class ForbiddenWordController {

    private final ForbiddenWordService forbiddenWordService;

    @GetMapping("/all")
    public List<ForbiddenWord> findAll() {
        return forbiddenWordService.findAll();
    }

    @GetMapping("/{wordId}")
    public ForbiddenWord findOne(@PathVariable Long wordId) {
        return forbiddenWordService.findOne(wordId);
    }

    @GetMapping("/cache")
    public List<String> findAllCache() {
        return ForbiddenWordCache.getForbiddenWords();
    }

    @PostMapping
    public ForbiddenWord save(@ModelAttribute String word) {
        return forbiddenWordService.save(word);
    }

    @PatchMapping("/{wordId}")
    public void updateWord(@PathVariable Long wordId, @ModelAttribute String word) {
        forbiddenWordService.updateWord(wordId, word);
    }

    @DeleteMapping("/{wordId}")
    public void deleteWord(@PathVariable Long wordId) {
        forbiddenWordService.deleteWord(wordId);
    }
}
