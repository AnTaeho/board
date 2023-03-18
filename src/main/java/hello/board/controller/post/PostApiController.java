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
import org.springframework.validation.BindingResult;
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

    @GetMapping("/{postId}")
    public ResponseEntity<PostResDto> findSinglePost(@PathVariable Long postId) {
        PostResDto findPost = postService.findSinglePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findPost);
    }

    @GetMapping("/member")
    public ResponseEntity<List<PostResDto>> findAllByMember(@RequestParam Long memberId) {
        List<PostResDto> posts = findAllPostOfMember(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

    private List<PostResDto> findAllPostOfMember(Long memberId) {
        return postService.findPostsOfMember(memberId);
    }

    @PostMapping("/post")
    public ResponseEntity<PostWriteResDto> writePost(HttpServletRequest request, @Valid @ModelAttribute PostWriteReqDto postWriteReqDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        PostWriteResDto writtenPost = postService.writePost(findLoginMember(request), postWriteReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(writtenPost);
    }

    @PatchMapping("/edit/{postId}")
    public ResponseEntity<PostUpdateResDto> updatePost(@PathVariable Long postId, @Valid @ModelAttribute PostUpdateReqDto postUpdateReqDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        PostUpdateResDto updatedPost = postService.updatePost(postId, postUpdateReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPost);

    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("post delete");
    }

    //세션에서 로그인 되어 있는 멤버 찾는 메서드
    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
