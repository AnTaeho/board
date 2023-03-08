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

    //게시글에 달린 모든 댓글 메서드
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResDto>> findCommentsByPost(@PathVariable @ModelAttribute Long postId) {
        List<CommentResDto> comments = commentService.findCommentsByPost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    //댓글 상세정보 화면 메서드
    @GetMapping("/info/{commentId}")
    public ResponseEntity<CommentResDto> commentInfo(@PathVariable Long commentId) {
        CommentResDto comment = commentService.findCommentDetail(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comment);
    }

    //현재 댓글만 전체 조회하는 화면 없음
    @GetMapping
    public ResponseEntity<List<CommentResDto>> findAllComment() {
        List<CommentResDto> comments = commentService.findAllComments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    //댓글 작성하는 메서드
    //알람과 관련된 쿼리 확인 필요
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResDto> writeComment(@PathVariable Long postId, @Valid @ModelAttribute CommentWriteDto writeDto, HttpServletRequest request) {
        CommentResDto writtenComment = commentService.writeComment(postId, findLoginMember(request), writeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(writtenComment);
    }

    //댓글 수정 메서드
    @PatchMapping("/edit")
    public ResponseEntity<CommentResDto> updateComment(@RequestParam Long commentId, @Valid @ModelAttribute CommentUpdateDto commentUpdateDto) {
        CommentResDto updateComment = commentService.updateComment(commentId, commentUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateComment);
    }

    //댓글 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("comment delete");
    }

    //댓글 좋아요 메서드
    @PatchMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId, HttpServletRequest request) {
        String result = commentService.likeComment(commentId, findLoginMember(request).getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
