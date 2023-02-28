package hello.board.controller.member;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    //회원 가입 메서드
    @PostMapping("/login/join")
    public ResponseEntity<MemberRegisterResDto> joinMember(@Valid @RequestBody MemberRegisterReqDto memberRegisterReqDto) {
        MemberRegisterResDto memberRegisterResDto = memberService.joinMember(memberRegisterReqDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberRegisterResDto);
    }

    //로그인 메서드
    //세션에 로그인 정보를 저장한다.
    @PostMapping("/login")
    public ResponseEntity<MemberRegisterResDto> login(@Valid @RequestBody LoginFormDto form, BindingResult bindingResult,
                        HttpServletRequest request) {

        if (!(request.getSession(false) == null)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginMember);

        if (loginMember.getRole() == MemberRole.ADMIN) {
            session.setAttribute("admin", "admin");
        } else {
            session.setAttribute("user", "user");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MemberRegisterResDto(loginMember));
    }

    //로그아웃 메서드
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            //현재 필터때문에 로그인을 하지 않으면 로그아웃 URL 로 접근도 불가능하다.
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("로그인 되어 있지 않습니다.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("로그아웃 성공");
    }
}

