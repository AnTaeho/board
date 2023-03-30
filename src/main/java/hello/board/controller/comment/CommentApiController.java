package hello.board.controller.comment;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResDto>> findCommentsByPost(@PathVariable final Long postId) {
        final List<CommentResDto> comments = commentService.findCommentsOfPost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @GetMapping("/info/{commentId}")
    public ResponseEntity<CommentResDto> commentInfo(@PathVariable final Long commentId) {
        final CommentResDto comment = commentService.findCommentDetail(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResDto>> findAllComment() {
        final List<CommentResDto> comments = commentService.findAllComments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResDto> writeComment(@PathVariable final Long postId,
                                                      @Valid @RequestBody final CommentWriteDto writeDto,
                                                      HttpServletRequest request) {
        final CommentResDto writtenComment = commentService.writeComment(postId, findLoginMember(request), writeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(writtenComment);
    }

    @PostMapping("/post/{postId}/{commentId}")
    public ResponseEntity<CommentResDto> writeChildComment(@PathVariable final Long postId,
                                                           @PathVariable final Long commentId,
                                                           @Valid @RequestBody final CommentWriteDto writeDto,
                                                           HttpServletRequest request) {
        final Member loginMember = findLoginMember(request);
        final CommentResDto comment = commentService.writeChildComment(postId, commentId, loginMember, writeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    @PatchMapping("/edit/{commentId}")
    public ResponseEntity<CommentResDto> updateComment(@PathVariable final Long commentId,
                                                       @Valid @RequestBody final CommentUpdateDto commentUpdateDto) {
        final CommentResDto updateComment = commentService.updateComment(commentId, commentUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateComment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("comment delete");
    }

    @PatchMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable final Long commentId,
                                              HttpServletRequest request) {
        final String result = commentService.likeComment(commentId, findLoginMember(request).getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
