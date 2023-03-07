package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.FollowRepository;
import hello.board.domain.member.repository.MemberRepository;
import hello.board.domain.notification.entity.Notification;
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
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;

    public PostResDto findSinglePost(Long id) {
        Post post = postRepository.findByIdWithFetchJoinMember(id).orElseThrow();
        return new PostResDto(post);
    }

    public List<PostResDto> findMemberPost(Long memberId) {
        Member findMember = findMember(memberId);
        return findMember.getPosts()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    public Page<PostResDto> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResDto::new);
    }

    @Transactional
    public PostWriteResDto writePost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        Post post = savePost(loginMember, postWriteReqDto);
        for (Member member : followRepository.findAllByToMember(loginMember)) {
            member.addNotification(saveNotification(loginMember, post, member));
        }
        return new PostWriteResDto(post);
    }

    private Notification saveNotification(Member loginMember, Post post, Member member) {
        return notificationRepository.save(new Notification(loginMember.getName(), member, post));
    }

    private Post savePost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        return postRepository.save(createPost(loginMember, postWriteReqDto));
    }

    private Post createPost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        return Post.builder()
                .title(postWriteReqDto.getTitle())
                .content(postWriteReqDto.getContent())
                .member(loginMember)
                .build();
    }

    @Transactional
    public PostUpdateResDto updatePost(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post findPost = findPost(id);
        findPost.updateInfo(postUpdateReqDto);
        return new PostUpdateResDto(findPost);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }
}
