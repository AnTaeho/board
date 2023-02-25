package hello.board.controller.member;

import hello.board.controller.dto.req.MemberReqDto;
import hello.board.controller.dto.res.MemberResDto;
import hello.board.entity.member.Member;
import hello.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/member/edit")
    public MemberResDto updateMember(@RequestParam Long id, @ModelAttribute MemberReqDto updateMember, HttpServletResponse response) throws IOException {

        Member updatedMember = memberService.updateMember(id, new Member(updateMember));

        String redirect_uri="/member?id=" + id;
        response.sendRedirect(redirect_uri);

        return new MemberResDto(updatedMember);
    }

    @DeleteMapping("/members/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "delete success";
    }

}
