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
    public ResponseEntity findById(@RequestParam Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(memberService.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //멤버 정보 수정 메서드
    //수정후 멤버 상세정보 화면으로 리다이렉팅
    @PatchMapping("/edit")
    public ResponseEntity updateMember(@RequestParam Long memberId, @RequestBody MemberUpdateReqDto memberUpdateReqDto) {

        try {

            MemberUpdateResDto memberUpdateResDto = memberService.updateMember(memberId, memberUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(memberUpdateResDto);
        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //멤버 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("member delete");
    }

}
