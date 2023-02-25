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
@RequestMapping
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{postId}")
    public String findSinglePost(@PathVariable Long postId, Model model) {
        Post findPost = postService.findSinglePost(postId);
        PostResDto postResDto = new PostResDto(findPost);
        model.addAttribute("post", postResDto);
        return "posts/post";
    }

    @GetMapping("/posts/member")
    public String findAllByMember(@RequestParam Long memberId, Model model) {
        List<PostResDto> posts = postService.findMemberPost(memberId)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable Long postId, Model model) {
        Post findPost = postService.findSinglePost(postId);
        PostResDto postResDto = new PostResDto(findPost);
        model.addAttribute("post", postResDto);
        return "posts/editPost";
    }

    @GetMapping("/posts")
    public String findAllPost(Model model) {
        List<PostResDto> posts = postService.findAllPost()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/posts/post")
    public String addPost(@ModelAttribute("post") Post post) {
        return "posts/addPost";
    }
}
