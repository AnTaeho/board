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

    @PostMapping("/{memberId}/follow")
    public ResponseEntity<String> followMember(@PathVariable final Long memberId,
                                               HttpServletRequest request) {
        final String result = memberService.followMember(memberId, findLoginMember(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<MemberResDto> findById(@RequestParam Long id) {
        final MemberResDto findMember = memberService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findMember);
    }

    @PatchMapping("/edit/{memberId}")
    public ResponseEntity<MemberUpdateResDto> updateMember(@PathVariable("memberId") final Long memberId,
                                                           @Valid @RequestBody final MemberUpdateReqDto memberUpdateReqDto) {
        final MemberUpdateResDto memberUpdateResDto = memberService.updateMember(memberId, memberUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUpdateResDto);
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable("memberId") final Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("member delete");
    }

    @GetMapping("/{memberId}/follow/average")
    public ResponseEntity<Double> findFollowerAgeAverage(@PathVariable Long memberId) {
        final double ageAverage = memberService.findFollowerAgeAverage(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ageAverage);
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }

}
