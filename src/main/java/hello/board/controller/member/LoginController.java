package hello.board.controller.member;

import hello.board.controller.dto.req.MemberReqDto;
import hello.board.controller.member.session.SessionConst;
import hello.board.entity.member.Member;
import hello.board.service.MemberService;
import hello.board.entity.member.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login/join")
    public Member joinMember(@Valid @ModelAttribute MemberReqDto memberReqDto) {
        return memberService.joinMember(new Member(memberReqDto));
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        String redirect_uri="/home";
        response.sendRedirect(redirect_uri);

        return "Login Success";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            return "현재 로그인 되어 있지 않습니다.";
        }

        String redirect_uri="/home";
        response.sendRedirect(redirect_uri);

        return "Logout Success";
    }
}

