package hello.board.controller.home;

import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final PostService postService;

    //멤버 전체 조회 화면 메서드
    @GetMapping("/members")
    public ResponseEntity<List<MemberResDto>> findAll(@RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<MemberResDto> members = findAllMember(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(members);
    }

    //모든 게시글 화면 메서드
    @GetMapping("/posts")
    public ResponseEntity<List<PostResDto>> findAllPost(@RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<PostResDto> posts = findAllPost(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

    private List<MemberResDto> findAllMember(Pageable pageable) {
        return memberService.findAll(pageable)
                .stream()
                .map(MemberResDto::new)
                .collect(Collectors.toList());
    }

    private List<PostResDto> findAllPost(Pageable pageable) {
        return postService.findAllPost(pageable)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }
}
