package hello.board.service;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.service.MemberService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @DisplayName("회원 가입 테스트")
    void joinTest() {
        MemberRegisterReqDto reqDto = new MemberRegisterReqDto("name", 20, "aaa", "aaa", MemberRole.ADMIN);
        MemberRegisterResDto resDto = memberService.joinMember(reqDto);

        assertThat(reqDto).isNotNull();
        assertThat(reqDto.getName()).isEqualTo(resDto.getName());
        assertThat(reqDto.getAge()).isEqualTo(resDto.getAge());
        assertThat(reqDto.getLoginId()).isEqualTo(resDto.getLoginId());
        assertThat(reqDto.getPassword()).isEqualTo(resDto.getPassword());
        assertThat(reqDto.getRole()).isEqualTo(resDto.getRole());
    }

    @Test
    @Transactional
    @DisplayName("회원 조회 테스트")
    void findByIdTest() {
        MemberResDto byId = memberService.findById(1L);

        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo("안태호");
    }

    @Test
    @Transactional
    @DisplayName("전체 회원 조회 테스트")
    void findAllTest() {
        Page<MemberResDto> all = memberService.findAll(Pageable.unpaged());
        for (MemberResDto memberResDto : all) {
            System.out.println("memberResDto = " + memberResDto.getName());
        }
        assertThat(all.getSize()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("회원 정보 수정 테스트")
    void updateMemberTest() {
        MemberUpdateReqDto updateReqDto = new MemberUpdateReqDto("aaa", 26, "aaa");
        MemberUpdateResDto updateResDto = memberService.updateMember(1L, updateReqDto);

        assertThat(updateResDto.getName()).isEqualTo(updateReqDto.getName());
        assertThat(updateResDto.getAge()).isEqualTo(updateReqDto.getAge());
        assertThat(updateResDto.getLoginId()).isEqualTo(updateReqDto.getLoginId());
    }

    @Test
    @Transactional
    @DisplayName("회원 삭제 테스트")
    void deleteMemberTest() {
        memberService.deleteMember(1L);

        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("로그인 테스트")
    void loginTest() {
        LoginFormDto form = new LoginFormDto("AnID", "AnPW");
        Member loginMember = memberService.login(form);

        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getLoginId()).isEqualTo(form.getLoginId());
        assertThat(loginMember.getPassword()).isEqualTo(form.getPassword());
    }

    @Test
    @Transactional
    @DisplayName("회원 팔로우 테스트")
    void followTest() {
        Member fromMember = memberService.findMember(1L);

        String followResult = memberService.followMember(5L, fromMember);
        assertThat(followResult).isEqualTo("follow success");

        String followAgainResult = memberService.followMember(5L, fromMember);
        assertThat(followAgainResult).isEqualTo("unfollow success");

    }

}
