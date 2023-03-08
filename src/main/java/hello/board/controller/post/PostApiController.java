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
import javax.validation.Valid;
import java.util.List;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    //게시글 상세 화면 메서드
    //쿼리 성능 발전 가능성 있음 -> 멤버 쿼리도 나감
    @GetMapping("/{postId}")
    public ResponseEntity<PostResDto> findSinglePost(@PathVariable Long postId) {
        PostResDto findPost = postService.findSinglePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findPost);
    }

    //회원의 모든 게시글 화면 메서드
    //쿼리 성능 발전 가능성 있음
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
    //팔로워가 있으면 쿼리 작살남
    @PostMapping("/post")
    public ResponseEntity<PostWriteResDto> writePost(HttpServletRequest request, @Valid @ModelAttribute PostWriteReqDto postWriteReqDto) {
        PostWriteResDto writtenPost = postService.writePost(findLoginMember(request), postWriteReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(writtenPost);
    }

    //게시글 수정 메서드
    //쿼리 성능 발전 가능성 있음
    @PatchMapping("/edit/{postId}")
    public ResponseEntity<PostUpdateResDto> updatePost(@PathVariable Long postId, @Valid @ModelAttribute PostUpdateReqDto postUpdateReqDto) {
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
