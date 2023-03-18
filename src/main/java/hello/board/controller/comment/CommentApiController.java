package hello.board.controller.comment;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<List<CommentResDto>> findCommentsByPost(@PathVariable Long postId) {
        List<CommentResDto> comments = commentService.findCommentsOfPost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @GetMapping("/info/{commentId}")
    public ResponseEntity<CommentResDto> commentInfo(@PathVariable Long commentId) {
        CommentResDto comment = commentService.findCommentDetail(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResDto>> findAllComment() {
        List<CommentResDto> comments = commentService.findAllComments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResDto> writeComment(@PathVariable Long postId, @Valid @ModelAttribute CommentWriteDto writeDto,
                                                      BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        CommentResDto writtenComment = commentService.writeComment(postId, findLoginMember(request), writeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(writtenComment);
    }

    @PatchMapping("/edit/{commentId}")
    public ResponseEntity<CommentResDto> updateComment(@PathVariable Long commentId, @Valid @ModelAttribute CommentUpdateDto commentUpdateDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        CommentResDto updateComment = commentService.updateComment(commentId, commentUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateComment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("comment delete");
    }

    @PatchMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId, HttpServletRequest request) {
        String result = commentService.likeComment(commentId, findLoginMember(request).getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    //세션에서 로그인 되어 있는 멤버 찾는 메서드
    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
