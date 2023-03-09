package hello.board.domain.comment.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService;

    private final Long memberId = 1L;
    private final Long postId = 2L;
    private final Long commentId = 3L;

    @Test
    void findAllComments() {
        List<CommentResDto> allComments = commentService.findAllComments();

        assertThat(allComments.size()).isEqualTo(3);
    }

    @Test
    void findCommentsByPost() {
        List<CommentResDto> commentsByPost = commentService.findCommentsByPost(postId);

        assertThat(commentsByPost.size()).isEqualTo(1);

        for (CommentResDto commentResDto : commentsByPost) {
            assertThat(commentResDto.getWriter()).isEqualTo("안태호");
        }
    }

    @Test
    void writeComment() {
        Member commentMember = memberService.findMember(memberId);
        CommentWriteDto content = new CommentWriteDto("content");

        CommentResDto commentResDto = commentService.writeComment(postId, commentMember, content);

        assertThat(commentResDto.getContent()).isEqualTo(content.getContent());
        assertThat(commentResDto.getWriter()).isEqualTo(commentMember.getName());
    }

    @Test
    void updateComment() {
        CommentUpdateDto newContent = new CommentUpdateDto("new content");

        CommentResDto commentResDto = commentService.updateComment(commentId, newContent);

        assertThat(commentResDto.getContent()).isEqualTo(newContent.getContent());
    }

    @Test
    void deleteComment() {
        commentService.deleteComment(commentId);

        assertThatThrownBy(() -> commentService.findComment(commentId))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    void likeComment() {
        String likeResult = commentService.likeComment(commentId, memberId);
        String likeAgainResult = commentService.likeComment(commentId, memberId);

        assertThat(likeResult).isEqualTo("Like deleted");
        assertThat(likeAgainResult).isEqualTo("Like success");
    }

}