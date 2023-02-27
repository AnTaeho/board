package hello.board.controller.comment;

import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService commentService;

    //현재 댓글만 전체 조회하는 화면 없음
    @GetMapping
    public List<CommentResDto> findAllComment() {
        return commentService.findAllComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    //댓글 작성하는 메서드
    //댓글 목록으로 리다이렉팅
    //리쿼스트 리스폰스 삭제하는 방법이 있는 것으로 기억한다.
    @PostMapping("/{postId}")
    public CommentResDto writeComment(@PathVariable Long postId, @ModelAttribute CommentWriteDto writeDto, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //세션에 저장되어 있는 로그인된 멤버를 가져온다
        Member loginMember = findLoginMember(request);
        Comment writtenComment = commentService.writeComment(postId, loginMember, writeDto.getContent());

        response.sendRedirect("/comment/post/" + postId);

        return new CommentResDto(writtenComment);
    }

    //댓글 수정 메서드
    //댓글 상세정보로 리다이렉팅
    //리쿼스트 리스폰스 삭제하는 방법이 있는 것으로 기억한다.
    @PostMapping("/edit")
    public void updateComment(@RequestParam Long commentId, String content, HttpServletResponse response) throws IOException {
        commentService.updateComment(commentId, content);
        response.sendRedirect("/comment/info/" + commentId);
    }

    //댓글 삭제 메서드
    //삭제 기능 화면은 아직 미구현.
    @DeleteMapping("/{commentId}}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    //댓글 좋아요 메서드
    //댓글 좋아요 화면은 아직 미구현.
    @PatchMapping("/{commentId}/{memberId}")
    public ResponseEntity likeComment(@PathVariable Long commentId, @PathVariable Long memberId) {
        String result = commentService.likeComment(commentId, memberId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
