package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.follow.FollowRepository;
import hello.board.domain.notification.entity.PostNotification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 단일 게시글 찾기 메서드
     * 게시글 아이디를 받아서 게시글 폼을 반환
     * @return PostResDto
     */
    public PostResDto findSinglePost(Long postId) {
        Post findPost = findPostWithMemberInfo(postId);
        return new PostResDto(findPost);
    }

    /**
     * 회원의 모든 게시글을 찾는 메서드
     * 회원의 아이디를 받아서 모든 게시글을 리스트 형식으로 반환
     * 페이징 기능 추가 가능
     * @return List<PostResDto>
     */
    public List<PostResDto> findPostsOfMember(Long memberId) {
        return postRepository.findPostsOfMember(memberId)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 모든 게시글을 찾는 메서드
     * 모든 게시글을 찾아서 페이징해서 반환한다.
     * @return Page<PostResDto>
     */
    public Page<PostResDto> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResDto::new);
    }

    /**
     * 게시글 작성 메서드
     * 입력 폼과 로그인한 회원를 받아서
     * 저장한 후에 로그인한 회원의 팔로워들에게 알람을 보낸다.
     * @return PostWriteResDto
     */
    @Transactional
    public PostWriteResDto writePost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        Post post = savePost(loginMember, postWriteReqDto);
        for (Member member : followRepository.findAllByToMember(loginMember)) {
            saveNotification(loginMember, post, member);
        }
        return new PostWriteResDto(post);
    }

    //입력 관련 메서드
    private Post savePost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        return postRepository.save(Post.createPost(loginMember, postWriteReqDto));
    }

    private void saveNotification(Member loginMember, Post post, Member member) {
        notificationRepository.save(new PostNotification(loginMember.getName(), member, post));
    }

    /**
     * 게시글 수정 메서드
     * 수정 폼과 해당 게시글 아이디를 받아서
     * 수정후 폼 반환
     * @return PostUpdateResDto
     */
    @Transactional
    public PostUpdateResDto updatePost(Long postId, PostUpdateReqDto postUpdateReqDto) {
        Post findPost = findPostWithMemberInfo(postId);
        findPost.updateInfo(postUpdateReqDto);
        return new PostUpdateResDto(findPost);
    }

    /**
     * 게시글 삭제 메서드
     * 게시글 아이디를 받아서 삭제한다.
     */
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    //공용 메서드
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Post findPostWithMemberInfo(Long postId) {
        return postRepository.findPostWithMemberInfo(postId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",postId));
                });
    }
}
