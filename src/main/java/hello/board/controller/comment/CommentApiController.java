package hello.board.controller.comment;

import hello.board.controller.dto.req.CommentReqDto;
import hello.board.controller.dto.res.CommentResDto;
import hello.board.controller.member.session.SessionConst;
import hello.board.entity.comment.Comment;
import hello.board.entity.member.Member;
import hello.board.service.CommentService;
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

    @PostMapping("/{postId}")
    public CommentResDto writeComment(@PathVariable Long postId, @ModelAttribute CommentReqDto comment, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        Comment writtenComment = commentService.writeComment(postId, loginMember.getName(), comment.getContent());

        String redirect_uri="/comment/post/" + postId;
        response.sendRedirect(redirect_uri);


        return new CommentResDto(writtenComment);
    }

    @PostMapping("/edit")
    public String updateComment(@RequestParam Long commentId, String content, HttpServletResponse response) throws IOException {
        commentService.updateComment(commentId, content);

        String redirect_uri="/comment/info/" + commentId;
        response.sendRedirect(redirect_uri);

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
