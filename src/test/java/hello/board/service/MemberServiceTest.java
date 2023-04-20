package hello.board.service;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.repository.member.MemberRepository;
import hello.board.domain.member.service.MemberService;
import hello.board.exception.notfound.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

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
        //given
        Member member = Member.builder().build();
        memberRepository.save(member);

        //when
        Member findMember = memberService.findMember(member.getId());

        //then
        assertThat(findMember).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("전체 회원 조회 테스트")
    void findAllTest() {
        //given
        Member member = Member.builder().build();
        memberRepository.save(member);
        Member member2 = Member.builder().build();
        memberRepository.save(member2);
        Member member3 = Member.builder().build();
        memberRepository.save(member3);

        //when
        Slice<MemberResDto> all = memberService.findAll(Pageable.unpaged());

        //then
        assertThat(all.getSize()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("회원 정보 수정 테스트")
    void updateMemberTest() {
        //given
        Member memberA = Member.builder()
                .name("memberA")
                .age(20)
                .loginId("aa")
                .build();
        memberRepository.save(memberA);
        MemberUpdateReqDto updateReqDto = new MemberUpdateReqDto("aaa", 26, "aaa");

        //when
        MemberUpdateResDto updateResDto = memberService.updateMember(memberA.getId(), updateReqDto);

        //then
        assertThat(updateResDto.getName()).isEqualTo(updateReqDto.getName());
        assertThat(updateResDto.getAge()).isEqualTo(updateReqDto.getAge());
        assertThat(updateResDto.getLoginId()).isEqualTo(updateReqDto.getLoginId());
    }

    @Test
    @Transactional
    @DisplayName("회원 삭제 테스트")
    void deleteMemberTest() {
        //given
        Member member = Member.builder().build();
        memberRepository.save(member);

        //when
        memberService.deleteMember(member.getId());

        //then
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("로그인 테스트")
    void loginTest() {
        MemberRegisterReqDto reqDto = new MemberRegisterReqDto("name", 20, "aaa", "aaa", MemberRole.ADMIN);
        memberService.joinMember(reqDto);

        LoginFormDto form = new LoginFormDto("aaa", "aaa");
        Member loginMember = memberService.login(form);

        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getLoginId()).isEqualTo(form.getLoginId());
        assertThat(loginMember.getPassword()).isEqualTo(form.getPassword());
    }

    @Test
    @Transactional
    @DisplayName("회원 팔로우 테스트")
    void followTest() {
        Member memberA = Member.builder().build();
        Member memberB = Member.builder().build();
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member fromMember = memberService.findMember(memberA.getId());

        String followResult = memberService.followMember(memberB.getId(), fromMember);
        assertThat(followResult).isEqualTo("follow success");

        String followAgainResult = memberService.followMember(memberB.getId(), fromMember);
        assertThat(followAgainResult).isEqualTo("unfollow success");

    }

}
