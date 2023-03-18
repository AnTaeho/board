package hello.board.controller.member;

import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.AllMemberInfoDto;
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

    //어드민 컨트롤러로 옮길 예정
    @GetMapping("/all/{memberId}")
    public ResponseEntity<AllMemberInfoDto> findAllInfoOfMember(@PathVariable Long memberId) {
        AllMemberInfoDto allInfo = memberService.findAllInfo(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allInfo);
    }

    @PostMapping("/{memberId}/follow")
    public ResponseEntity<String> followMember(@PathVariable Long memberId, HttpServletRequest request) {
        String result = memberService.followMember(memberId, findLoginMember(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<MemberResDto> findById(@RequestParam Long id) {
        MemberResDto findMember = memberService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findMember);
    }

    @PatchMapping("/edit/{memberId}")
    public ResponseEntity<MemberUpdateResDto> updateMember(@PathVariable("memberId") Long memberId, @Valid @ModelAttribute MemberUpdateReqDto memberUpdateReqDto) {
        MemberUpdateResDto memberUpdateResDto = memberService.updateMember(memberId, memberUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUpdateResDto);
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("member delete");
    }

    //세션에서 로그인 되어 있는 멤버 찾는 메서드
    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }

}
