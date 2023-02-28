package hello.board.controller.home;

import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final PostService postService;

    //멤버 전체 조회 화면 메서드
    @GetMapping("/members")
    public ResponseEntity<Page<MemberResDto>> findAll(@RequestParam("page") int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<MemberResDto> members = memberService.findAll(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(members);
    }

    //모든 게시글 화면 메서드
    @GetMapping("/posts")
    public ResponseEntity<Page<PostResDto>> findAllPost(@RequestParam("page") int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostResDto> posts = postService.findAllPost(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

}
