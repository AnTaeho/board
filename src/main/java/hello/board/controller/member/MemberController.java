package hello.board.controller.member;

import hello.board.controller.dto.res.MemberResDto;
import hello.board.entity.member.Member;
import hello.board.entity.member.MemberRole;
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

    //회원가입 화면 메서드
    @GetMapping("/login/join")
    public String joinForm(@ModelAttribute("member") Member member, Model model) {
        MemberRole[] memberRoles = MemberRole.values();
        model.addAttribute("memberRoles", memberRoles);
        return "members/addMember";
    }

    //로그인 화면 메서드
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    //멤버 전체 조회 화면 메서드
    //현재 멤버 전체 조회하는 화면 미구현.
    @GetMapping("/members")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/members";
    }

    //멤버 상세정보 메서드
    @GetMapping("/member")
    public String findById(@RequestParam Long id, Model model) {
        Member findMember = memberService.findById(id);
        model.addAttribute("member", new MemberResDto(findMember));
        return "members/member";
    }

    //멤버 수정 화면 메서드
    @GetMapping("/member/edit")
    public String updateMember(@RequestParam Long id, Model model) {
        Member findMember = memberService.findById(id);
        model.addAttribute("member", new MemberResDto(findMember));
        return "members/editMember";
    }

}
