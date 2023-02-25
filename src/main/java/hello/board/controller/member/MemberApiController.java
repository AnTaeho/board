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

    //멤버 정보 수정 메서드
    //수정후 멤버 상세정보 화면으로 리다이렉팅
    @PostMapping("/member/edit")
    public MemberResDto updateMember(@RequestParam Long id, @ModelAttribute MemberReqDto updateMember, HttpServletResponse response) throws IOException {

        Member updatedMember = memberService.updateMember(id, new Member(updateMember));

        String redirect_uri="/member?id=" + id;
        response.sendRedirect(redirect_uri);

        return new MemberResDto(updatedMember);
    }

    //멤버 삭제 메서드
    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }

}
