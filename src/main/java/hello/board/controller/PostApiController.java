package hello.board.controller;

import hello.board.controller.dto.req.PostReqDto;
import hello.board.controller.dto.res.PostResDto;
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
    public PostResDto findSinglePost(@PathVariable Long postId) {
        Post findPost = postService.findSinglePost(postId);
        return new PostResDto(findPost);
    }

    @GetMapping("/member/{memberId}")
    public List<PostResDto> findAllByMember(@PathVariable Long memberId) {
        return postService.findMemberPost(memberId)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PostResDto> findAllPost() {
        return postService.findAllPost()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/member/{memberId}")
    public PostResDto writePost(@PathVariable Long memberId, @ModelAttribute PostReqDto postReqDto) {
        Post writtenPost = postService.writePost(memberId, new Post(postReqDto));
        return new PostResDto(writtenPost);
    }

    @PostMapping("/{id}")
    public PostResDto updatePost(@PathVariable Long id, @ModelAttribute PostReqDto updatePost) {
        Post updatedPost = postService.updatePost(id, new Post(updatePost));
        return new PostResDto(updatedPost);
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "delete success";
    }
}
