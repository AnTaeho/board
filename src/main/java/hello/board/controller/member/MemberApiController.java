package hello.board.controller.member;

import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    //멤버 상세정보 메서드
    @GetMapping
    public ResponseEntity<MemberResDto> findById(@RequestParam Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findById(id));
    }

    //멤버 정보 수정 메서드
    //수정후 멤버 상세정보 화면으로 리다이렉팅
    @PostMapping("/edit")
    public ResponseEntity<MemberUpdateResDto> updateMember(@RequestParam Long memberId, @RequestBody MemberUpdateReqDto memberUpdateReqDto) {

        MemberUpdateResDto memberUpdateResDto = memberService.updateMember(memberId, memberUpdateReqDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUpdateResDto);
    }

    //멤버 삭제 메서드
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }

}
