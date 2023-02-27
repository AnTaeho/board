package hello.board.controller.member;

import hello.board.controller.member.dto.req.MemberReqDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    //멤버 정보 수정 메서드
    //수정후 멤버 상세정보 화면으로 리다이렉팅
    @PostMapping("/edit")
    public MemberResDto updateMember(@RequestParam Long memberId, @ModelAttribute MemberReqDto memberReqDto, HttpServletResponse response) throws IOException {

        Member updatedMember = memberService.updateMember(memberId, Member.from(memberReqDto));

        response.sendRedirect("/member?id=" + memberId);

        return new MemberResDto(updatedMember);
    }

    //멤버 삭제 메서드
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }

}
