package hello.board.controller;

import hello.board.controller.dto.CommentDto;
import hello.board.entity.Comment;
import hello.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> findAllComment() {
        return commentService.findAllComments()
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> findCommentsByPost(@PathVariable Long postId) {
        return commentService.findCommentsByPost(postId)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/post/{postId}")
    public CommentDto writeComment(@PathVariable Long postId, String content) {
        Comment writtenComment = commentService.writeComment(postId, content);
        return new CommentDto(writtenComment);
    }

    @PostMapping("/{commentId}}")
    public String updateComment(@PathVariable Long commentId, String content) {
        commentService.updateComment(commentId, content);
        return "update success";
    }

    @DeleteMapping("/{commentId}}")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "delete success";
    }
}
