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

import static hello.board.controller.member.session.SessionConst.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final MemberService memberService;

    //회원 가입 메서드
    @PostMapping("/login/join")
    public Member joinMember(@Valid @ModelAttribute MemberReqDto memberReqDto) {
        return memberService.joinMember(new Member(memberReqDto));
    }

    //로그인 메서드
    //세션에 로그인 정보를 저장한다.
    //로그인 실패 화면 아직 미구현.
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

        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginMember);

        String redirect_uri="/home";
        response.sendRedirect(redirect_uri);

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

        String redirect_uri="/home";
        response.sendRedirect(redirect_uri);

        return "Logout Success";
    }
}

