package hello.board.controller;

import hello.board.controller.dto.MemberDto;
import hello.board.entity.Member;
import hello.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    //ResponseEntity 알아보기
    @GetMapping
    public List<MemberDto> findAll() {
        List<Member> members = memberService.findAll();
        return members.stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MemberDto findById(@PathVariable("id") Long id) {
        Member findMember = memberService.findById(id);
        return new MemberDto(findMember);
    }

    @PostMapping
    public Member joinMember(Member member) {
        return memberService.joinMember(member.getName(), member.getAge());
    }

    @PostMapping("/{id}")
    public String updateMember(@PathVariable("id") Long id, Member updateMember) {
        memberService.updateMember(id, updateMember);
        return "update success";
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "delete success";
    }
}
