package hello.board.controller.member;

import hello.board.entity.member.Member;
import hello.board.entity.member.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class MemberController {

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

}
