package hello.board.controller.member;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberReqDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final MemberService memberService;

    //회원 가입 메서드
    @PostMapping("/login/join")
    public Member joinMember(@Valid @ModelAttribute MemberReqDto memberReqDto) {
        return memberService.joinMember(Member.from(memberReqDto));
    }

    //로그인 메서드
    //세션에 로그인 정보를 저장한다.
    //로그인 실패 화면 아직 미구현.
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto form, BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            return "Login Fail";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "Login Fail";
        }

        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginMember);

        if (loginMember.getRole() == MemberRole.ADMIN) {
            session.setAttribute(MemberRole.ADMIN.getDescription(), loginMember);
        }

        response.sendRedirect("/home");

        return "Login Success";
    }

    //로그아웃 메서드
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            //현재 필터때문에 로그인을 하지 않으면 로그아웃 URL 로 접근도 불가능하다.
            return "현재 로그인 되어 있지 않습니다.";
        }

        response.sendRedirect("/home");

        return "Logout Success";
    }
}

