package hello.board.controller.post;

import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/search")
    public ResponseEntity<Page<PostResDto>> searchPost(@ModelAttribute final PostSearchCondition condition,
                                                       @RequestParam("page") final int page) {
        final PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        final Page<PostResDto> searchedPost = postService.searchPost(condition, pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(searchedPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResDto> findSinglePost(@PathVariable final Long postId) {
        final PostResDto findPost = postService.findSinglePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findPost);
    }

    @GetMapping("/member")
    public ResponseEntity<List<PostResDto>> findAllByMember(@RequestParam final Long memberId) {
        final List<PostResDto> posts = postService.findPostsOfMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<PostWriteResDto> writePost(HttpServletRequest request,
                                                     @Valid @RequestBody final PostWriteReqDto postWriteReqDto) {
        final PostWriteResDto writtenPost = postService.writePost(findLoginMember(request), postWriteReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(writtenPost);
    }

    @PatchMapping("/edit/{postId}")
    public ResponseEntity<PostUpdateResDto> updatePost(@PathVariable final Long postId,
                                                       @Valid @RequestBody final PostUpdateReqDto postUpdateReqDto) {
        final PostUpdateResDto updatedPost = postService.updatePost(postId, postUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPost);

    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable final Long postId) {
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
