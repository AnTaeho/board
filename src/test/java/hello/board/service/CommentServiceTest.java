package hello.board.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import hello.board.exception.notfound.CommentNotFoundException;
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
        //given
        testSetting();

        //when
        List<CommentResDto> allComments = commentService.findAllComments();

        //then
        assertThat(allComments.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("게시글의 전체 댓글 조회 테스트")
    void findAllCommentOfPost() {
        //given
        PostWriteResDto postWriteResDto = testSetting();

        //when
        List<CommentResDto> commentsByPost = commentService.findCommentsOfPost(postWriteResDto.getId());
        Post post = postService.findPost(postWriteResDto.getId());

        //then
        assertThat(commentsByPost.size()).isEqualTo(3);
        assertThat(commentsByPost.get(0).getWriter()).isEqualTo(post.getMember().getName());
    }

    // TODO : 회원의 댓글 조회 및 테스트

    @Test
    @Transactional
    @DisplayName("댓글 작성 테스트")
    void writeCommentTest() {
        //given
        Member member = registerMember();
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
        CommentWriteDto writeDto = new CommentWriteDto("content");

        //when
        CommentResDto commentResDto = commentService.writeComment(postWriteResDto.getId(), member, writeDto);

        //then
        assertThat(commentResDto.getWriter()).isEqualTo(member.getName());
        assertThat(commentResDto.getContent()).isEqualTo(writeDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        //given
        Member member = registerMember();
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
        CommentWriteDto writeDto = new CommentWriteDto("content");
        CommentResDto commentResDto = commentService.writeComment(postWriteResDto.getId(), member, writeDto);

        //when
        CommentUpdateDto updateDto = new CommentUpdateDto("new-content");
        CommentResDto updateResDto = commentService.updateComment(commentResDto.getId(), updateDto);

        //then
        assertThat(updateResDto.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        //given
        Member member = registerMember();
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
        CommentWriteDto writeDto = new CommentWriteDto("content");
        CommentResDto commentResDto = commentService.writeComment(postWriteResDto.getId(), member, writeDto);

        //when
        commentService.deleteComment(commentResDto.getId());

        //then
        assertThatThrownBy(() -> commentService.findComment(commentResDto.getId()))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("댓글 좋아요 테스트")
    void likeCommentTest() {
        //given
        Member member = registerMember();
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
        CommentWriteDto writeDto = new CommentWriteDto("content");
        CommentResDto commentResDto = commentService.writeComment(postWriteResDto.getId(), member, writeDto);

        //when
        Member otherMember = registerMember();
        String likeResult = commentService.likeComment(commentResDto.getId(), otherMember.getId());
        assertThat(likeResult).isEqualTo("Like success");

        //then
        String likeAgainResult = commentService.likeComment(commentResDto.getId(), otherMember.getId());
        assertThat(likeAgainResult).isEqualTo("Like deleted");

    }

    private PostWriteResDto testSetting() {
        Member member = registerMember();
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
        commentService.writeComment(postWriteResDto.getId(), member, new CommentWriteDto("content"));
        commentService.writeComment(postWriteResDto.getId(), member, new CommentWriteDto("content"));
        commentService.writeComment(postWriteResDto.getId(), member, new CommentWriteDto("content"));
        return postWriteResDto;
    }

    private Member registerMember() {
        MemberRegisterReqDto registerReqDto = new MemberRegisterReqDto("memberA", 20, "aaa", "aaa", MemberRole.ADMIN);
        MemberRegisterResDto memberRegisterResDto = memberService.joinMember(registerReqDto);
        return memberService.findMember(memberRegisterResDto.getId());
    }
}
