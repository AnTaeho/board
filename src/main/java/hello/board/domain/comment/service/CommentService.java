package hello.board.domain.comment.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.comment.repository.commentlike.CommentLikeRepository;
import hello.board.domain.comment.repository.comment.CommentRepository;
import hello.board.domain.forbiddenword.service.ForbiddenWordCache;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.member.MemberRepository;
import hello.board.domain.notification.entity.CommentLikeNotification;
import hello.board.domain.notification.entity.CommentNotification;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.support.annotation.CreateTransactional;
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

    public List<CommentResDto> findCommentsOfPost(final Long postId) {
        return postRepository.findPostWithCommentInfo(postId).getComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    @CreateTransactional
    public CommentResDto writeComment(final Long postId, final Member commentMember,
                                      final CommentWriteDto writeDto) {
        final Post findPost = postRepository.findWithMemberByPostId(postId);
        final Comment newComment = Comment.makeComment(commentMember, writeDto.getContent(), findPost);
        ForbiddenWordCache.checkCommentForbiddenWord(newComment);
        if (isNotMyPost(commentMember, findPost)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    private boolean isNotMyPost(final Member commentMember, final Post findPost) {
        return !findPost.getMember().getId().equals(commentMember.getId());
    }

    private Notification makeCommentNotification(final Member commentMember, final Post findPost,
                                                 final Comment newComment) {
        final Member notificatiedMember = findPost.getMember();
        return CommentNotification.from(commentMember.getName(), notificatiedMember, newComment);
    }

    @CreateTransactional
    public CommentResDto writeChildComment(final Long postId, final Long commentId,
                                           final Member commentMember, final CommentWriteDto writeDto) {
        final Post findPost = postRepository.findPostWithCommentInfo(postId);
        final Comment findComment = commentRepository.findComment(commentId);
        final Comment newComment = Comment.makeChildComment(commentMember, writeDto.getContent(), findPost, findComment);
        ForbiddenWordCache.checkCommentForbiddenWord(newComment);
        if (isNotMyParentComment(commentMember, findComment)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    private boolean isNotMyParentComment(Member commentMember, Comment findComment) {
        return !commentMember.getId().equals(findComment.getCommentMember().getId());
    }

    @Transactional
    public CommentResDto updateComment(final Long commentId, final CommentUpdateDto commentUpdateDto) {
        Comment findComment = commentRepository.findComment(commentId);
        findComment.updateInfo(commentUpdateDto.getContent());
        ForbiddenWordCache.checkCommentForbiddenWord(findComment);
        return new CommentResDto(findComment);
    }

    @Transactional
    public void deleteComment(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public String likeComment(final Long commentId, final Long memberId) {

        final Comment findComment = commentRepository.findCommentWithMemberInfo(commentId);
        CommentLike notLiked = findLike(commentId, memberId);

        if (notLiked == null) {
            commentLikeRepository.save(pressCommentLike(memberId, findComment));
            return "Like success";
        } else {
            commentLikeRepository.delete(notLiked);
            return "Like deleted";
        }
    }

    private CommentLike findLike(final Long commentId, final Long memberId) {
        return commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId);
    }

    private CommentLike pressCommentLike(final Long memberId, final Comment findComment) {

        final Member commentOwner = findCommentOwner(findComment);
        final Member loginMember = memberRepository.findMember(memberId);

        if (commentOwner == loginMember) {
            return CommentLike.makeCommentLike(loginMember, findComment);
        }

        return CommentLike.makeCommentLikeWithNotification(loginMember, findComment,
                makeCommentLikeNotification(loginMember, commentOwner));
    }

    private Member findCommentOwner(final Comment findComment) {
        return findComment.getPost().getMember();
    }

    private CommentLikeNotification makeCommentLikeNotification(final Member loginMember, final Member commentOwner) {
        return notificationRepository.save(CommentLikeNotification.from(loginMember, commentOwner));
    }

    public CommentResDto findCommentDetail(final Long commentId) {
        return new CommentResDto(commentRepository.findComment(commentId));
    }

}
