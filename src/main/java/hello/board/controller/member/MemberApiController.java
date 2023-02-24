package hello.board.controller.member;

import hello.board.controller.dto.res.MemberResDto;
import hello.board.entity.Member;
import hello.board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping
    public List<MemberResDto> findAll() {
        List<Member> members = memberService.findAll();
        return members.stream()
                .map(MemberResDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MemberResDto findById(@PathVariable("id") Long id) {
        Member findMember = memberService.findById(id);
        return new MemberResDto(findMember);
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
