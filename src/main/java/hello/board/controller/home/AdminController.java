package hello.board.controller.home;

import hello.board.controller.member.dto.res.AllMemberInfoDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/members")
    public ResponseEntity<Slice<MemberResDto>> findAll(@RequestParam("page") final int page) {
        final PageRequest pageRequest = PageRequest.of(page, 10);
        final Slice<MemberResDto> members = memberService.findAll(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(members);
    }

    @GetMapping("/all/{memberId}")
    public ResponseEntity<AllMemberInfoDto> findAllInfoOfMember(@PathVariable final Long memberId) {
        final AllMemberInfoDto allInfo = memberService.findAllInfo(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allInfo);
    }

    @GetMapping("/posts/waiting")
    public ResponseEntity<Slice<PostResDto>> findAllWaitingPost(@RequestParam("page") final int page) {
        final Pageable pageable = PageRequest.of(page, 10);
        final Slice<PostResDto> allWaitingPost = postService.findAllWaitingPost(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allWaitingPost);
    }

    @PatchMapping("/posts/waiting/{postId}")
    public ResponseEntity<PostResDto> updatePostToPosted(@PathVariable final Long postId) {
        final PostResDto postResDto = postService.updatePostToPosted(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResDto);
    }
}
