package hello.board.controller.post;

import hello.board.controller.dto.req.PostReqDto;
import hello.board.controller.dto.res.PostResDto;
import hello.board.entity.member.Member;
import hello.board.entity.post.Post;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;
//
//    @GetMapping("/member/{memberId}")
//    public List<PostResDto> findAllByMember(@PathVariable Long memberId) {
//        return postService.findMemberPost(memberId)
//                .stream()
//                .map(PostResDto::new)
//                .collect(Collectors.toList());
//    }

    @PostMapping("/post")
    public PostResDto writePost(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PostReqDto postReqDto) throws IOException {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        Post writtenPost = postService.writePost(loginMember.getId(), new Post(postReqDto));

        String redirect_uri="/posts";
        response.sendRedirect(redirect_uri);

        return new PostResDto(writtenPost);
    }

    @PostMapping("/{id}/edit")
    public PostResDto updatePost(@PathVariable Long id, @ModelAttribute PostReqDto updatePost, HttpServletResponse response) throws IOException {
        Post updatedPost = postService.updatePost(id, new Post(updatePost));

        String redirect_uri="/posts/" + id;
        response.sendRedirect(redirect_uri);

        return new PostResDto(updatedPost);
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "delete success";
    }
}
