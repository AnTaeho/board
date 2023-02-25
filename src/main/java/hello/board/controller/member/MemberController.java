package hello.board.controller.member;

import hello.board.controller.dto.res.MemberResDto;
import hello.board.entity.member.Member;
import hello.board.entity.member.login.LoginForm;
import hello.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login/join")
    public String joinForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @GetMapping("/members")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/members";
    }

    @GetMapping("/member")
    public String findById(@RequestParam Long id, Model model) {
        Member findMember = memberService.findById(id);
        model.addAttribute("member", new MemberResDto(findMember));
        return "members/member";
    }

    @GetMapping("/member/edit")
    public String updateMember(@RequestParam Long id, Model model) {
        Member findMember = memberService.findById(id);
        model.addAttribute("member", new MemberResDto(findMember));
        return "members/editMember";
    }

}
