package hello.board.domain.member.service;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private final Long memberId = 1L;
    private final Long memberId2 = 6L;

    @Test
    void joinMember() {
        MemberRegisterReqDto registerReqDto = new MemberRegisterReqDto("안태호", 26, "AnID2", "AnPW2", MemberRole.ADMIN);
        MemberRegisterResDto memberRegisterResDto = memberService.joinMember(registerReqDto);

        assertThat(memberRegisterResDto.getName()).isEqualTo(registerReqDto.getName());
        assertThat(memberRegisterResDto.getAge()).isEqualTo(registerReqDto.getAge());
        assertThat(memberRegisterResDto.getLoginId()).isEqualTo(registerReqDto.getLoginId());
        assertThat(memberRegisterResDto.getPassword()).isEqualTo(registerReqDto.getPassword());
        assertThat(memberRegisterResDto.getRole()).isEqualTo(registerReqDto.getRole());
    }

    @Test
    void findById() {
        MemberResDto findMember = memberService.findById(memberId);

        assertThat(findMember.getName()).isEqualTo("안태호");
        assertThat(findMember.getAge()).isEqualTo(20);
        assertThat(findMember.getRole()).isEqualTo(MemberRole.ADMIN);
    }

    @Test
    void findAll() {
        Page<MemberResDto> allMembers = memberService.findAll(Pageable.unpaged());

        assertThat(allMembers.getSize()).isEqualTo(3);
    }

    @Test
    void updateMember() {
        MemberUpdateReqDto updateReqDto = new MemberUpdateReqDto("anan", 150, "anan");
        MemberUpdateResDto updateResDto = memberService.updateMember(memberId, updateReqDto);

        assertThat(updateResDto.getName()).isEqualTo(updateReqDto.getName());
        assertThat(updateResDto.getAge()).isEqualTo(updateReqDto.getAge());
        assertThat(updateResDto.getLoginId()).isEqualTo(updateReqDto.getLoginId());

    }

    @Test
    void deleteMember() {
        memberService.deleteMember(memberId);

        assertThatThrownBy(() -> memberService.findById(memberId))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    void login() {
        LoginFormDto loginFormDto = new LoginFormDto("AnID", "AnPW");
        Member loginMember = memberService.login(loginFormDto);

        assertThat(loginMember.getName()).isEqualTo("안태호");
        assertThat(loginMember.getAge()).isEqualTo(20);
        assertThat(loginMember.getLoginId()).isEqualTo("AnID");
        assertThat(loginMember.getPassword()).isEqualTo("AnPW");
        assertThat(loginMember.getRole()).isEqualTo(MemberRole.ADMIN);
    }

    @Test
    void followMember() {
        Member fromMember = memberService.findMember(memberId2);
        String followFirstResult = memberService.followMember(memberId, fromMember);
        assertThat(followFirstResult).isEqualTo("follow success");

        String followAgainResult = memberService.followMember(memberId, fromMember);
        assertThat(followAgainResult).isEqualTo("unfollow success");
    }

}