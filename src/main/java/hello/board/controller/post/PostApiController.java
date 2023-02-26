package hello.board.controller.post;

import hello.board.controller.post.dto.req.PostReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static hello.board.controller.member.session.SessionConst.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    //게시글 작성 메서드
    //세션에서 저장된 로그인 멤버를 가져온다.
    //작성후 게시글 목록으로 리다이렉팅
    @PostMapping("/post")
    public PostResDto writePost(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PostReqDto postReqDto) throws IOException {

        //로그인 멤버를 찾아온다.
        Member loginMember = findLoginMember(request);
        Post writtenPost = postService.writePost(loginMember.getId(), new Post(postReqDto));

        response.sendRedirect("/posts");

        return new PostResDto(writtenPost);
    }

    //게시글 수정 메서드
    //수정후 게시글 목록으로 리다이렉팅
    @PostMapping("/edit/{postId}")
    public PostResDto updatePost(@PathVariable Long postId, @ModelAttribute PostReqDto updatePost, HttpServletResponse response) throws IOException {
        Post updatedPost = postService.updatePost(postId, new Post(updatePost));

        response.sendRedirect("/posts/" + postId);

        return new PostResDto(updatedPost);
    }

    //게시글 삭제 메서드
    //게시글 삭제 화면은 아직 미구현.
    @DeleteMapping("{commentId}")
    public void deletePost(@PathVariable Long commentId) {
        postService.deletePost(commentId);
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
