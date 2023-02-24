package hello.board.controller.member;

import hello.board.controller.dto.MemberDto;
import hello.board.controller.member.session.SessionConst;
import hello.board.entity.Member;
import hello.board.service.member.MemberService;
import hello.board.service.member.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    //ResponseEntity 알아보기
    @GetMapping
    public List<MemberDto> findAll() {
        List<Member> members = memberService.findAll();
        return members.stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MemberDto findById(@PathVariable("id") Long id) {
        Member findMember = memberService.findById(id);
        return new MemberDto(findMember);
    }

    @PostMapping
    public Member joinMember(@Valid @ModelAttribute Member member) {
        return memberService.joinMember(member);
    }

    @PostMapping("/{id}")
    public String updateMember(@PathVariable("id") Long id, Member updateMember) {
        memberService.updateMember(id, updateMember);
        return "update success";
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "delete success";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                          HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "Login Fail";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "Login Fail";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "Login Success";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            return "현재 로그인 되어 있지 않습니다.";
        }
        return "Logout Success";
    }
}
