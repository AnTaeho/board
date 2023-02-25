package hello.board.controller.post;

import hello.board.controller.dto.res.PostResDto;
import hello.board.entity.post.Post;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    //게시글 상세 화면 메서드
    @GetMapping("/{postId}")
    public String findSinglePost(@PathVariable Long postId, Model model) {
        Post findPost = postService.findSinglePost(postId);
        model.addAttribute("post", new PostResDto(findPost));
        return "posts/post";
    }

    //회원은 모든 게시글 화면 메서드
    @GetMapping("/member")
    public String findAllByMember(@RequestParam Long memberId, Model model) {

        //회원은 모든 게시글을 찾아온다.
        List<PostResDto> posts = findAllPostOfMember(memberId);
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    //게시글 수정 화면 메서드
    @GetMapping("/edit/{postId}")
    public String updatePost(@PathVariable Long postId, Model model) {
        Post findPost = postService.findSinglePost(postId);
        model.addAttribute("post", new PostResDto(findPost));
        return "posts/editPost";
    }

    //모든 게시글 화면 메서드
    @GetMapping
    public String findAllPost(Model model) {

        //모든 게시글을 찾아온다.
        List<PostResDto> posts = findAllPost();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    //게시글 작성 화면 메서드
    @GetMapping("/post")
    public String addPost(@ModelAttribute("post") Post post) {
        return "posts/addPost";
    }

    private List<PostResDto> findAllPost() {
        return postService.findAllPost()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    private List<PostResDto> findAllPostOfMember(Long memberId) {
        return postService.findMemberPost(memberId)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }
}
