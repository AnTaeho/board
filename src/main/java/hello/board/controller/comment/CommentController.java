package hello.board.controller.comment;

import hello.board.controller.dto.res.CommentResDto;
import hello.board.controller.dto.res.PostResDto;
import hello.board.entity.comment.Comment;
import hello.board.entity.member.Member;
import hello.board.entity.post.Post;
import hello.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/info/{commentId}")
    public String commentInfo(@PathVariable Long commentId, Model model) {
        Comment comment = commentService.findComment(commentId);
        Long postId = comment.getPost().getId();
        model.addAttribute("postId", postId);
        model.addAttribute("comment", new CommentResDto(comment));
        return "comment/comment";
    }

    @GetMapping("/comment/{postId}")
    public String joinForm(@PathVariable @ModelAttribute Long postId, @ModelAttribute("comment") Comment comment) {
        return "comment/addComment";
    }

    @GetMapping("/comment/post/{postId}")
    public String findCommentsByPost(@PathVariable Long postId, Model model) {
        List<CommentResDto> comments = commentService.findCommentsByPost(postId)
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());

        model.addAttribute("id", postId);
        model.addAttribute("comments", comments);
        return "comment/postComments";
    }

    @GetMapping("/comment/edit")
    public String updateComment(@RequestParam Long commentId, Model model) {
        Comment comment = commentService.findComment(commentId);
        CommentResDto commentResDto = new CommentResDto(comment);
        model.addAttribute("comment", commentResDto);
        return "comment/editComment";
    }
}
