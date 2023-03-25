package hello.board.service;

import hello.board.controller.forbiddenword.dto.req.AddWordDto;
import hello.board.controller.forbiddenword.dto.req.UpdateWordDto;
import hello.board.controller.forbiddenword.dto.res.WordResDto;
import hello.board.domain.forbiddenword.service.ForbiddenWordService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ForbiddenWordServiceTest {

    @Autowired
    private ForbiddenWordService forbiddenWordService;

    @Test
    @Transactional
    @DisplayName("금지어를 저장한다.")
    void saveTest() {
        //given
        AddWordDto addWordDto = new AddWordDto("fuck");

        //when
        WordResDto resDto = forbiddenWordService.save(addWordDto);

        //then
        assertThat(addWordDto.getWord()).isEqualTo(resDto.getWord());
    }

    @Test
    @Transactional
    @DisplayName("모든 금지어를 조회한다.")
    void findAllTest() {
        //given
        AddWordDto addWordDto = new AddWordDto("fuck");
        AddWordDto addWordDto2 = new AddWordDto("shit");
        forbiddenWordService.save(addWordDto);
        forbiddenWordService.save(addWordDto2);

        //when
        List<String> allWord = forbiddenWordService.findAll()
                .stream()
                .map(WordResDto::getWord)
                .collect(Collectors.toList());

        //then
        assertThat(allWord.size()).isEqualTo(2);
        assertThat(allWord).contains(addWordDto.getWord(), addWordDto2.getWord());
    }

    @Test
    @Transactional
    @DisplayName("단일 금지어를 조회한다.")
    void findOneTest() {
        //given
        AddWordDto addWordDto = new AddWordDto("fuck");
        WordResDto resDto = forbiddenWordService.save(addWordDto);

        //when
        WordResDto findWord = forbiddenWordService.findOne(resDto.getId());

        //then
        assertThat(findWord.getWord()).isEqualTo(addWordDto.getWord());
    }

    @Test
    @Transactional
    @DisplayName("금지어를 수정한다.")
    void updateWordTest() {
        //given
        AddWordDto addWordDto = new AddWordDto("fuck");
        WordResDto resDto = forbiddenWordService.save(addWordDto);

        //when
        UpdateWordDto updateWordDto = new UpdateWordDto("fXXX");
        forbiddenWordService.updateWord(resDto.getId(), updateWordDto);

        WordResDto findWord = forbiddenWordService.findOne(resDto.getId());

        //then
        assertThat(findWord.getWord()).isEqualTo(updateWordDto.getWord());
    }

    @Test
    @Transactional
    @DisplayName("금지어를 삭제한다.")
    void deleteWordTest() {
        //given
        AddWordDto addWordDto = new AddWordDto("fuck");
        WordResDto resDto = forbiddenWordService.save(addWordDto);

        //when
        forbiddenWordService.deleteWord(resDto.getId());

        //then
        assertThatThrownBy(() -> forbiddenWordService.findOne(resDto.getId()))
                .isInstanceOf(CustomNotFoundException.class);
    }

}
