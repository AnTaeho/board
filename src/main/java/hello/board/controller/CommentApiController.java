package hello.board.controller;

import hello.board.controller.dto.res.CommentResDto;
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
    public List<CommentResDto> findAllComment() {
        return commentService.findAllComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/post/{postId}")
    public List<CommentResDto> findCommentsByPost(@PathVariable Long postId) {
        return commentService.findCommentsByPost(postId)
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/post/{postId}/{memberId}")
    public CommentResDto writeComment(@PathVariable Long postId, @PathVariable Long memberId, String content) {
        Comment writtenComment = commentService.writeComment(postId, memberId, content);
        return new CommentResDto(writtenComment);
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
