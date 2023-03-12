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

    //join
    @Test
    @Transactional
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

    //findById
    @Test
    @Transactional
    void findByIdTest() {
        MemberResDto byId = memberService.findById(1L);

        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo("안태호");
    }

    //findAll
    @Test
    @Transactional
    void findAllTest() {
        Page<MemberResDto> all = memberService.findAll(Pageable.unpaged());
        for (MemberResDto memberResDto : all) {
            System.out.println("memberResDto = " + memberResDto.getName());
        }
        assertThat(all.getSize()).isEqualTo(3);
    }

    //update
    @Test
    @Transactional
    void updateMemberTest() {
        MemberUpdateReqDto updateReqDto = new MemberUpdateReqDto("aaa", 26, "aaa");
        MemberUpdateResDto updateResDto = memberService.updateMember(1L, updateReqDto);

        assertThat(updateResDto.getName()).isEqualTo(updateReqDto.getName());
        assertThat(updateResDto.getAge()).isEqualTo(updateReqDto.getAge());
        assertThat(updateResDto.getLoginId()).isEqualTo(updateReqDto.getLoginId());
    }

    //delete

    @Test
    @Transactional
    void deleteMemberTest() {
        memberService.deleteMember(1L);

        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(CustomNotFoundException.class);
    }

    //login
    @Test
    @Transactional
    void loginTest() {
        LoginFormDto form = new LoginFormDto("AnID", "AnPW");
        Member loginMember = memberService.login(form);

        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getLoginId()).isEqualTo(form.getLoginId());
        assertThat(loginMember.getPassword()).isEqualTo(form.getPassword());
    }

    //follow
    @Test
    @Transactional
    void followTest() {
        Member fromMember = memberService.findMember(1L);

        String followResult = memberService.followMember(5L, fromMember);
        assertThat(followResult).isEqualTo("follow success");

        String followAgainResult = memberService.followMember(5L, fromMember);
        assertThat(followAgainResult).isEqualTo("unfollow success");

    }

}
