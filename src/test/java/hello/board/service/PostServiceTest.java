//package hello.board.service;
//
//import hello.board.controller.forbiddenword.dto.req.AddWordDto;
//import hello.board.controller.member.dto.req.MemberRegisterReqDto;
//import hello.board.controller.member.dto.res.MemberRegisterResDto;
//import hello.board.controller.post.dto.req.PostUpdateReqDto;
//import hello.board.controller.post.dto.req.PostWriteReqDto;
//import hello.board.controller.post.dto.res.PostResDto;
//import hello.board.controller.post.dto.res.PostUpdateResDto;
//import hello.board.controller.post.dto.res.PostWriteResDto;
//import hello.board.domain.forbiddenword.service.ForbiddenWordService;
//import hello.board.domain.member.entity.Member;
//import hello.board.domain.member.entity.MemberRole;
//import hello.board.domain.member.service.MemberService;
//import hello.board.domain.post.entity.Post;
//import hello.board.domain.post.entity.PostStatus;
//import hello.board.domain.post.repository.PostRepository;
//import hello.board.domain.post.service.PostService;
//import hello.board.exception.notfound.PostNotFoundException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//public class PostServiceTest {
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private ForbiddenWordService forbiddenWordService;
//
//    @Test
//    @Transactional
//    @DisplayName("단일 게시글 조회 테스트")
//    void findSinglePostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
//        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
//
//        //when
//        PostResDto singlePost = postService.findSinglePost(postWriteResDto.getId());
//
//        //then
//        assertThat(singlePost).isNotNull();
//        assertThat(singlePost.getMemberName()).isEqualTo(member.getName());
//        assertThat(singlePost.getTitle()).isEqualTo(writeReqDto.getTitle());
//        assertThat(singlePost.getContent()).isEqualTo(writeReqDto.getContent());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("전체 게시글 조회 테스트")
//    void findAllPostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto reqDto = new PostWriteReqDto("title", "content");
//        Post post1 = Post.createPost(member, reqDto);
//        Post post2 = Post.createPost(member, reqDto);
//        Post post3 = Post.createPost(member, reqDto);
//        postRepository.saveAll(List.of(post1, post2, post3));
//
//        //when
//        List<Post> all = postRepository.findAll();
////        Page<PostResDto> allPost = postService.findAllPostedPost(Pageable.unpaged());
//
//        //then
//        assertThat(all.size()).isEqualTo(3);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("회원의 게시글 조회 테스트")
//    void findMemberPostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto reqDto = new PostWriteReqDto("title", "content");
//        Post post1 = Post.createPost(member, reqDto);
//        Post post2 = Post.createPost(member, reqDto);
//        Post post3 = Post.createPost(member, reqDto);
//        postRepository.saveAll(List.of(post1, post2, post3));
//
//        //when
//        List<PostResDto> postsOfMember = postService.findPostsOfMember(member.getId());
//
//        //then
//        assertThat(postsOfMember.size()).isEqualTo(3);
//        for (PostResDto postResDto : postsOfMember) {
//            assertThat(postResDto.getMemberName()).isEqualTo(member.getName());
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("게시글 작성 테스트")
//    void writePostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
//
//        //when
//        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
//
//        //then
//        assertThat(postWriteResDto.getMemberName()).isEqualTo(member.getName());
//        assertThat(postWriteResDto.getTitle()).isEqualTo(writeReqDto.getTitle());
//        assertThat(postWriteResDto.getContent()).isEqualTo(writeReqDto.getContent());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("게시글 수정 테스트")
//    void updatePostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
//        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
//        PostUpdateReqDto updateReqDto = new PostUpdateReqDto("new-title", "new-content");
//
//        //when
//        PostUpdateResDto updatePost = postService.updatePost(postWriteResDto.getId(), updateReqDto);
//
//        //then
//        assertThat(updatePost.getTitle()).isEqualTo(updateReqDto.getTitle());
//        assertThat(updatePost.getContent()).isEqualTo(updateReqDto.getContent());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("게시글 삭제 테스트")
//    void deletePostTest() {
//        //given
//        Member member = registerMember();
//        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "content");
//        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
//
//        //when
//        postService.deletePost(postWriteResDto.getId());
//
//        //then
//        assertThatThrownBy(() -> postService.findPost(postWriteResDto.getId()))
//                .isInstanceOf(PostNotFoundException.class);
//    }
//
//    @Test
//    void postWithForbiddenTest() {
//        //given
//        forbiddenWordService.save(new AddWordDto("fuck"));
//
//        //when
//        Member member = registerMember();
//        PostWriteReqDto writeReqDto = new PostWriteReqDto("title", "good");
//        PostWriteResDto postWriteResDto = postService.writePost(member, writeReqDto);
//
//        PostWriteReqDto writeReqDto2 = new PostWriteReqDto("title", "fuck");
//        PostWriteResDto postWriteResDto2 = postService.writePost(member, writeReqDto2);
//
//        //then
//        Post post = postService.findPost(postWriteResDto.getId());
//        Post post2 = postService.findPost(postWriteResDto2.getId());
//
//        assertThat(post.getStatus()).isEqualTo(PostStatus.POSTED);
//        assertThat(post2.getStatus()).isEqualTo(PostStatus.WAITING_TO_POST);
//    }
//
//    private Member registerMember() {
//        MemberRegisterReqDto registerReqDto = new MemberRegisterReqDto("memberA", 20, "aaa", "aaa", MemberRole.ADMIN);
//        MemberRegisterResDto memberRegisterResDto = memberService.joinMember(registerReqDto);
//        return memberService.findMember(memberRegisterResDto.getId());
//    }
//}
