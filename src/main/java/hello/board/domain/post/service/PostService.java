package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.forbiddenword.service.ForbiddenWordCache;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.follow.FollowRepository;
import hello.board.domain.notification.entity.PostNotification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.notfound.PostNotFoundException;
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

    public PostResDto findSinglePost(final Long postId) {
        final Post findPost = findWithMemberByPostId(postId);
        return new PostResDto(findPost);
    }

    public List<PostResDto> findPostsOfMember(final Long memberId) {
        return postRepository.findPostsByMemberId(memberId)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    public Page<PostResDto> findAllPostedPost(final Pageable pageable) {
        return postRepository.findAllPostedPost(pageable);
    }

    public Page<PostResDto> findAllWaitingPost(final Pageable pageable) {
        return postRepository.findAllAllWaitingPost(pageable);
    }

    @Transactional
    public PostWriteResDto writePost(final Member loginMember, final PostWriteReqDto postWriteReqDto) {
        final Post post = savePost(loginMember, postWriteReqDto);
        for (Member member : followRepository.findAllByToMember(loginMember)) {
            saveNotification(loginMember, post, member);
        }
        return new PostWriteResDto(post);
    }

    private Post savePost(final Member loginMember, final PostWriteReqDto postWriteReqDto) {
        Post post = Post.createPost(loginMember, postWriteReqDto);
        checkForbiddenWord(post);
        return postRepository.save(post);
    }

    private void checkForbiddenWord(Post post) {
        if(ForbiddenWordCache.checkPostForbiddenWord(post)) {
            post.changeToWaitingPost();
        } else {
            post.changeToPosted();
        }
    }

    private void saveNotification(final Member loginMember, final Post post, final Member member) {
        notificationRepository.save(PostNotification.from(loginMember.getName(), member, post));
    }

    @Transactional
    public PostUpdateResDto updatePost(final Long postId, final PostUpdateReqDto postUpdateReqDto) {
        Post findPost = findWithMemberByPostId(postId);
        findPost.updateInfo(postUpdateReqDto);
        checkForbiddenWord(findPost);
        return new PostUpdateResDto(findPost);
    }

    @Transactional
    public PostResDto updatePostToPosted(final Long postId) {
        Post findPost = findPost(postId);
        findPost.changeToPosted();
        return new PostResDto(findPost);
    }

    @Transactional
    public void deletePost(final Long postId) {
        postRepository.deleteById(postId);
    }

    public Post findPost(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new PostNotFoundException();
                });
    }

    private Post findWithMemberByPostId(final Long postId) {
        return postRepository.findWithMemberAndCommentByPostId(postId)
                .orElseThrow(() -> {
                    throw new PostNotFoundException();
                });
    }

    public Page<PostResDto> searchPost(final PostSearchCondition condition, final Pageable pageable) {
        return postRepository.search(condition, pageable);
    }

}
