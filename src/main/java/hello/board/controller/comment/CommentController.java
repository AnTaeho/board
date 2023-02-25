package hello.board.controller.comment;

import hello.board.controller.dto.res.CommentResDto;
import hello.board.entity.comment.Comment;
import hello.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    //댓글 추가 화면 메서드
    @GetMapping("/{postId}")
    public String addComment(@PathVariable @ModelAttribute Long postId, @ModelAttribute("comment") Comment comment) {
        return "comment/addComment";
    }

    //게시글에 달린 모든 댓글 메서드
    @GetMapping("/post/{postId}")
    public String findCommentsByPost(@PathVariable Long postId, Model model) {

        //게시글의 모든 댓글을 찾는다.
        List<CommentResDto> comments = findAllCommentOfPost(postId);

        model.addAttribute("id", postId);
        model.addAttribute("comments", comments);
        return "comment/postComments";
    }

    //댓글 상세정보 화면 메서드
    @GetMapping("/info/{commentId}")
    public String commentInfo(@PathVariable Long commentId, Model model) {
        Comment comment = commentService.findComment(commentId);
        model.addAttribute("postId", comment.getPost().getId());
        model.addAttribute("comment", new CommentResDto(comment));
        return "comment/comment";
    }

    //댓글 수정 화면 메서드
    @GetMapping("/edit")
    public String updateComment(@RequestParam Long commentId, Model model) {
        Comment comment = commentService.findComment(commentId);
        model.addAttribute("comment", new CommentResDto(comment));
        return "comment/editComment";
    }

    private List<CommentResDto> findAllCommentOfPost(Long postId) {
        return commentService.findCommentsByPost(postId)
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }
}
