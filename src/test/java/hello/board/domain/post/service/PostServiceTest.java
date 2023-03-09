package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    private final Long postId = 2L;
    private final Long memberId = 1L;

    @Test
    void findSinglePost() {
        PostResDto findPost = postService.findSinglePost(postId);

        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findPost.getTitle()).isEqualTo("\"안산은 이게 일상이야\"");
        assertThat(findPost.getContent()).isEqualTo("대충 안산 이야기");
    }

    @Test
    void findMemberPost() {
        List<PostResDto> memberPost = postService.findMemberPost(memberId);

        for (PostResDto postResDto : memberPost) {
            assertThat(postResDto.getMemberName()).isEqualTo("안태호");
        }
    }

    @Test
    void findAllPost() {
        Page<PostResDto> allPost = postService.findAllPost(Pageable.unpaged());

        assertThat(allPost.getSize()).isEqualTo(3);
    }

    @Test
    void writePost() {
        Member loginMember = memberService.findMember(memberId);
        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");

        PostWriteResDto writeResDto = postService.writePost(loginMember, writeReqDto);

        assertThat(writeResDto.getTitle()).isEqualTo(writeReqDto.getTitle());
        assertThat(writeResDto.getContent()).isEqualTo(writeReqDto.getContent());
        assertThat(writeResDto.getMemberName()).isEqualTo(loginMember.getName());

    }

    @Test
    void updatePost() {
        PostUpdateReqDto updateReqDto = new PostUpdateReqDto("new title", "new content");

        PostUpdateResDto updatePost = postService.updatePost(postId, updateReqDto);

        assertThat(updatePost.getTitle()).isEqualTo(updateReqDto.getTitle());
        assertThat(updatePost.getContent()).isEqualTo(updateReqDto.getContent());
    }

    @Test
    void deletePost() {
        postService.deletePost(postId);

        assertThatThrownBy(() -> postService.findPost(postId))
                .isInstanceOf(CustomNotFoundException.class);
    }
}