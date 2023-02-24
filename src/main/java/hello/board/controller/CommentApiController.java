package hello.board.controller;

import hello.board.controller.dto.CommentDto;
import hello.board.entity.comment.Comment;
import hello.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/post/{postId}/{memberId}")
    public CommentDto writeComment(@PathVariable Long postId, @PathVariable Long memberId, String content) {
        Comment writtenComment = commentService.writeComment(postId, memberId, content);
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

    @PatchMapping("/{commentId}/{memberId}")
    public ResponseEntity likeComment(@PathVariable Long commentId, @PathVariable Long memberId) {
        String result = commentService.likeComment(commentId, memberId);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
