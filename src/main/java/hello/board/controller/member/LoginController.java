package hello.board.controller.member;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.service.MemberService;
import hello.board.exception.badrequest.AlreadyLoginBadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login/join")
    public ResponseEntity<MemberRegisterResDto> joinMember(@Valid @RequestBody final MemberRegisterReqDto memberRegisterReqDto) {
        final MemberRegisterResDto memberRegisterResDto = memberService.joinMember(memberRegisterReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberRegisterResDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberRegisterResDto> login(@Valid @RequestBody final LoginFormDto form,
                                                      HttpServletRequest request) {
        if (!(request.getSession(false) == null)) {
            throw new AlreadyLoginBadRequest();
        }
        final Member loginMember = memberService.login(form);
        HttpSession session = addMemberInSession(request, loginMember);
        checkAdmin(loginMember, session);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MemberRegisterResDto(loginMember));
    }

    private static HttpSession addMemberInSession(HttpServletRequest request, Member loginMember) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginMember);
        return session;
    }

    private void checkAdmin(Member loginMember, HttpSession session) {
        if (loginMember.getRole() == MemberRole.ADMIN) {
            session.setAttribute("admin", "관리자");
        } else {
            session.setAttribute("user", "일반 회원");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {


        HttpSession session = request.getSession(false);

        //현재 필터때문에 로그인을 하지 않으면 로그아웃 URL 로 접근도 불가능하다.
        //그래서 바로 아래 로직이 생기는 일은 없다.
        //하지만 혹시나하는 마음에 남겨둔다.
        if (session != null) {
            session.invalidate();
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("로그인 되어 있지 않습니다.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("로그아웃 성공");
    }
}

