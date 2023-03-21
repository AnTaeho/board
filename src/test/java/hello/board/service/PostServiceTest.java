package hello.board.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostServiceTest {

    private final Long postId = 2L;

    private final Long memberId1 = 1L;
    private final Long memberId2 = 5L;
    private final Long memberId3 = 9L;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @DisplayName("단일 게시글 조회 테스트")
    void findSinglePostTest() {
        PostResDto singlePost = postService.findSinglePost(postId);

        assertThat(singlePost).isNotNull();
        assertThat(singlePost.getId()).isEqualTo(postId);
    }

    @Test
    @Transactional
    @DisplayName("전체 게시글 조회 테스트")
    void findAllPostTest() {
        Page<PostResDto> allPost = postService.findAllPostedPost(Pageable.unpaged());

        assertThat(allPost.getSize()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("회원의 게시글 조회 테스트")
    void findMemberPostTest() {
        List<PostResDto> memberPost = postService.findPostsOfMember(memberId1);
        Member member1 = memberService.findMember(memberId1);

        assertThat(memberPost.size()).isEqualTo(1);
        assertThat(memberPost.get(0).getMemberName()).isEqualTo(member1.getName());

        List<PostResDto> memberPost1 = postService.findPostsOfMember(memberId2);
        Member member2 = memberService.findMember(memberId2);

        assertThat(memberPost1.size()).isEqualTo(1);
        assertThat(memberPost1.get(0).getMemberName()).isEqualTo(member2.getName());


        List<PostResDto> memberPost2 = postService.findPostsOfMember(memberId3);
        Member member3 = memberService.findMember(memberId3);

        assertThat(memberPost2.size()).isEqualTo(1);
        assertThat(memberPost2.get(0).getMemberName()).isEqualTo(member3.getName());


    }

    @Test
    @Transactional
    @DisplayName("게시글 작성 테스트")
    void writePostTest() {
        Member loginMember = memberService.findMember(memberId1);
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");

        PostWriteResDto writeResDto = postService.writePost(loginMember, writeReqDto);

        assertThat(writeResDto.getMemberName()).isEqualTo(loginMember.getName());
        assertThat(writeResDto.getTitle()).isEqualTo(writeReqDto.getTitle());
        assertThat(writeResDto.getContent()).isEqualTo(writeReqDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("게시글 수정 테스트")
    void updatePostTest() {
        PostUpdateReqDto updateReqDto = new PostUpdateReqDto("new-title", "new-content");

        PostUpdateResDto updatePost = postService.updatePost(postId, updateReqDto);

        assertThat(updatePost.getTitle()).isEqualTo(updateReqDto.getTitle());
        assertThat(updatePost.getContent()).isEqualTo(updateReqDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제 테스트")
    void deletePostTest() {
        postService.deletePost(postId);

        assertThatThrownBy(() -> postService.findPost(postId))
                .isInstanceOf(CustomNotFoundException.class);
    }
}
