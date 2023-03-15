package hello.board.domain.comment.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.comment.repository.commentlike.CommentLikeRepository;
import hello.board.domain.comment.repository.comment.CommentRepository;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.MemberRepository;
import hello.board.domain.notification.entity.CommentLikeNotification;
import hello.board.domain.notification.entity.CommentNotification;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final NotificationRepository notificationRepository;

    public List<CommentResDto> findAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    public List<CommentResDto> findCommentsByPost(Long postId) {
        return findPost(postId).getComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResDto writeComment(Long postId, Member commentMember, CommentWriteDto commentWriteDto) {
        Post findPost = findPostWithMemberInfo(postId);
        Comment newComment = new Comment(commentMember, commentWriteDto.getContent(), findPost);
        if (isNotMyPost(commentMember, findPost)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    private boolean isNotMyPost(Member commentMember, Post findPost) {
        return !findPost.getMember().getId().equals(commentMember.getId());
    }

    @Transactional
    public CommentResDto updateComment(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment findComment = findComment(commentId);
        findComment.updateInfo(commentUpdateDto.getContent());
        return new CommentResDto(findComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public String likeComment(Long commentId, Long memberId) {
        Comment findComment = findCommentWithMemberInfo(commentId);

        if (isNotLiked(commentId, memberId)) {
            commentLikeRepository.save(pressCommentLike(memberId, findComment));
            return "Like success";
        } else {
            commentLikeRepository.deleteByCommentIdAndMemberId(commentId, memberId);
            return "Like deleted";
        }
    }

    public CommentResDto findCommentDetail(Long commentId) {
        return new CommentResDto(findComment(commentId));
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",commentId));
                });
    }

    private Comment findCommentWithMemberInfo(Long commentId) {
        return commentRepository.findCommentWithMemberInfo(commentId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",commentId));
                });
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Post findPostWithMemberInfo(Long postId) {
        return postRepository.findByIdWithFetchJoinMember(postId)
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

    private Notification makeCommentNotification(Member commentMember, Post findPost, Comment newComment) {
        Member notificatiedMember = findPost.getMember();
        return new CommentNotification(commentMember.getName(), notificatiedMember, newComment);
    }

    private boolean isNotLiked(Long commentId, Long memberId) {
        return commentLikeRepository.hasNoLike(commentId, memberId);
    }

    private CommentLike pressCommentLike(Long memberId, Comment findComment) {

        Member commentOwner = findCommentOwner(findComment);
        Member loginMember = findMember(memberId);

        if (commentOwner == loginMember) {
            return new CommentLike(loginMember, findComment);
        }

        return new CommentLike(loginMember, findComment, makeCommentLikeNotification(loginMember, commentOwner));
    }

    private CommentLikeNotification makeCommentLikeNotification(Member loginMember, Member commentOwner) {
        return new CommentLikeNotification(loginMember, commentOwner);
    }

    private Member findCommentOwner(Comment findComment) {
        return findComment.getPost().getMember();
    }
}
