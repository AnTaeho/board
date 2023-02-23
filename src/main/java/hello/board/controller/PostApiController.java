package hello.board.controller;

import hello.board.controller.dto.PostDto;
import hello.board.entity.post.Post;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public PostDto findSinglePost(@PathVariable Long postId) {
        Post findPost = postService.findSinglePost(postId);
        return new PostDto(findPost);
    }

    @GetMapping("/member/{memberId}")
    public List<PostDto> findAllByMember(@PathVariable Long memberId) {
        return postService.findMemberPost(memberId)
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PostDto> findAllPost() {
        return postService.findAllPost()
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/member/{memberId}")
    public PostDto writePost(@PathVariable Long memberId, Post post) {
        Post writtenPost = postService.writePost(memberId, post);
        return new PostDto(writtenPost);
    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id, Post updatePost) {
        postService.updatePost(id, updatePost);
        return "update success";
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "delete success";
    }
}
