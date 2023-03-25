package hello.board.controller.home;

import hello.board.controller.member.dto.res.AllMemberInfoDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final PostService postService;

    //멤버 전체 조회 화면 메서드
    @GetMapping("/members")
    public ResponseEntity<Page<MemberResDto>> findAll(@RequestParam("page") final int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<MemberResDto> members = memberService.findAll(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(members);
    }

    //어드민 컨트롤러로 옮길 예정
    @GetMapping("/all/{memberId}")
    public ResponseEntity<AllMemberInfoDto> findAllInfoOfMember(@PathVariable final Long memberId) {
        AllMemberInfoDto allInfo = memberService.findAllInfo(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allInfo);
    }

    @GetMapping("/posts/waiting")
    public ResponseEntity<Page<PostResDto>> findAllWaitingPost(@RequestParam("page") final int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostResDto> allWaitingPost = postService.findAllWaitingPost(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allWaitingPost);
    }

    @PatchMapping("/posts/waiting/{postId}")
    public ResponseEntity<PostResDto> updatePostToPosted(@PathVariable final Long postId) {
        PostResDto postResDto = postService.updatePostToPosted(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResDto);
    }
}
