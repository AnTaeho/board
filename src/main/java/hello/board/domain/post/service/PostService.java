package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.FollowRepository;
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

    public PostResDto findSinglePost(Long postId) {
        Post findPost = findPostWithMemberInfo(postId);
        return new PostResDto(findPost);
    }

    public List<PostResDto> findPostsOfMember(Long memberId) {
        return postRepository.findPostsOfMember(memberId)
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
            saveNotification(loginMember, post, member);
        }
        return new PostWriteResDto(post);
    }

    private Post savePost(Member loginMember, PostWriteReqDto postWriteReqDto) {
        return postRepository.save(Post.createPost(loginMember, postWriteReqDto));
    }

    private void saveNotification(Member loginMember, Post post, Member member) {
        notificationRepository.save(new PostNotification(loginMember.getName(), member, post));
    }

    @Transactional
    public PostUpdateResDto updatePost(Long postId, PostUpdateReqDto postUpdateReqDto) {
        Post findPost = findPostWithMemberInfo(postId);
        findPost.updateInfo(postUpdateReqDto);
        return new PostUpdateResDto(findPost);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

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
