package hello.board.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @DisplayName("전체 댓글 조회 테스트")
    void findAllComment() {
        List<CommentResDto> allComments = commentService.findAllComments();

        assertThat(allComments.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("게시글의 전체 댓글 조회 테스트")
    void findAllCommentOfPost() {
        List<CommentResDto> commentsByPost = commentService.findCommentsByPost(2L);
        Post post = postService.findPost(2L);
        assertThat(commentsByPost.size()).isEqualTo(1);
        assertThat(commentsByPost.get(0).getWriter()).isEqualTo(post.getMember().getName());

        List<CommentResDto> commentsByPost2 = commentService.findCommentsByPost(6L);
        Post post2 = postService.findPost(6L);
        assertThat(commentsByPost2.size()).isEqualTo(1);
        assertThat(commentsByPost2.get(0).getWriter()).isEqualTo(post2.getMember().getName());

        List<CommentResDto> commentsByPost3 = commentService.findCommentsByPost(10L);
        Post post3 = postService.findPost(10L);
        assertThat(commentsByPost3.size()).isEqualTo(1);
        assertThat(commentsByPost3.get(0).getWriter()).isEqualTo(post3.getMember().getName());

    }

    // TODO : 회원의 댓글 조회 및 테스트

    @Test
    @Transactional
    @DisplayName("댓글 작성 테스트")
    void writeCommentTest() {
        Member loginMember = memberService.findMember(1L);
        CommentWriteDto writeDto = new CommentWriteDto("comment-content");
        CommentResDto commentResDto = commentService.writeComment(2L, loginMember, writeDto);

        assertThat(commentResDto.getWriter()).isEqualTo(loginMember.getName());
        assertThat(commentResDto.getContent()).isEqualTo(writeDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        CommentUpdateDto updateDto = new CommentUpdateDto("new-content");
        CommentResDto commentResDto = commentService.updateComment(3L, updateDto);

        assertThat(commentResDto.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        commentService.deleteComment(3L);

        assertThatThrownBy(() -> commentService.findComment(3L))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("댓글 좋아요 테스트")
    void likeCommentTest() {
        String likeResult = commentService.likeComment(3L, 5L);
        assertThat(likeResult).isEqualTo("Like success");

        String likeAgainResult = commentService.likeComment(3L, 5L);
        assertThat(likeAgainResult).isEqualTo("Like deleted");

    }
}
