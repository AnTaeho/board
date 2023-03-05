package hello.board.controller.post;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    //게시글 상세 화면 메서드
    @GetMapping("/{postId}")
    public ResponseEntity<PostResDto> findSinglePost(@PathVariable Long postId) {
        PostResDto findPost = postService.findSinglePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findPost);

    }

    //회원의 모든 게시글 화면 메서드
    @GetMapping("/member")
    public ResponseEntity<List<PostResDto>> findAllByMember(@RequestParam Long memberId) {
        List<PostResDto> posts = findAllPostOfMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

    private List<PostResDto> findAllPostOfMember(Long memberId) {
        return postService.findMemberPost(memberId);
    }

    //게시글 작성 메서드
    //세션에서 저장된 로그인 멤버를 가져온다.
    //작성후 게시글 목록으로 리다이렉팅
    @PostMapping("/post")
    public ResponseEntity<PostWriteResDto> writePost(HttpServletRequest request, @RequestBody PostWriteReqDto postWriteReqDto) {
        PostWriteResDto writtenPost = postService.writePost(findLoginMember(request), postWriteReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(writtenPost);
    }

    //게시글 수정 메서드
    //수정후 게시글 목록으로 리다이렉팅
    @PatchMapping("/edit/{postId}")
    public ResponseEntity<PostUpdateResDto> updatePost(@PathVariable Long postId, @RequestBody PostUpdateReqDto postUpdateReqDto) {
        PostUpdateResDto updatedPost = postService.updatePost(postId, postUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPost);

    }

    //게시글 삭제 메서드
    //게시글 삭제 화면은 아직 미구현.
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("post delete");
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
