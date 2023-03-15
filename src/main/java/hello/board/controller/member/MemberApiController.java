package hello.board.controller.member;

import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    //멤버 팔로우 메서드
    @PostMapping("/{memberId}/follow")
    public ResponseEntity<String> followMember(@PathVariable Long memberId, HttpServletRequest request) {
        String result = memberService.followMember(memberId, findLoginMember(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    //멤버 상세정보 메서드
    @GetMapping
    public ResponseEntity<MemberResDto> findById(@RequestParam Long id) {
        MemberResDto findMember = memberService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findMember);
    }

    //멤버 정보 수정 메서드
    @PatchMapping("/edit/{memberId}")
    public ResponseEntity<MemberUpdateResDto> updateMember(@PathVariable("memberId") Long memberId, @Valid @ModelAttribute MemberUpdateReqDto memberUpdateReqDto) {
        MemberUpdateResDto memberUpdateResDto = memberService.updateMember(memberId, memberUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUpdateResDto);
    }

    //멤버 삭제 메서드
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("member delete");
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }

}
