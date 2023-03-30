package hello.board.domain.comment.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.comment.repository.commentlike.CommentLikeRepository;
import hello.board.domain.comment.repository.comment.CommentRepository;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.member.MemberRepository;
import hello.board.domain.notification.entity.CommentLikeNotification;
import hello.board.domain.notification.entity.CommentNotification;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.notfound.CommentNotFoundException;
import hello.board.exception.notfound.MemberNotFoundException;
import hello.board.exception.notfound.PostNotFoundException;
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

    /**
     * 모든 댓글 조회 메서드
     * 모든 댓글을 찾아서 반환한다.
     * @return List<CommentResDto>
     */
    public List<CommentResDto> findAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 게시글의 모든 댓글 조회 메서드
     * 게시글 아이디를 받아서 해당 게시글의 모든 댓글을 반환한다.
     * @return List<CommentResDto>
     */
    public List<CommentResDto> findCommentsOfPost(final Long postId) {
        return findPostWithCommentInfo(postId).getComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 작성 메서드
     * 게시글 아이디, 로그인한 회원, 작성 폼을받아서
     * 댓글을 작성하고, 본인의 게시글이 아닌경우 알람 발생
     * @return CommentResDto
     */
    @Transactional
    public CommentResDto writeComment(final Long postId, final Member commentMember, final CommentWriteDto writeDto) {
        final Post findPost = findPostWithMemberInfo(postId);
        final Comment newComment = Comment.makeComment(commentMember, writeDto.getContent(), findPost);
        if (isNotMyPost(commentMember, findPost)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    private boolean isNotMyPost(final Member commentMember, final Post findPost) {
        return !findPost.getMember().getId().equals(commentMember.getId());
    }

    private Notification makeCommentNotification(final Member commentMember, final Post findPost, final Comment newComment) {
        final Member notificatiedMember = findPost.getMember();
        return CommentNotification.from(commentMember.getName(), notificatiedMember, newComment);
    }

    @Transactional
    public CommentResDto writeChildComment(final Long postId, final Long commentId, final Member commentMember, final CommentWriteDto writeDto) {
        final Post findPost = findPostWithCommentInfo(postId);
        final Comment findComment = findComment(commentId);
        final Comment newComment = Comment.makeChildComment(commentMember, writeDto.getContent(), findPost, findComment);
        if (isNotMyPost(commentMember, findPost)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    /**
     * 댓글 수정 메서드
     * 댓글 아이디와 수정 폼을 받아서
     * 해당 댓글의 내용을 수정한다.
     * @return CommentResDto
     */
    @Transactional
    public CommentResDto updateComment(final Long commentId, final CommentUpdateDto commentUpdateDto) {
        Comment findComment = findComment(commentId);
        findComment.updateInfo(commentUpdateDto.getContent());
        return new CommentResDto(findComment);
    }

    /**
     * 댓글 삭제 메서드
     * 댓글의 아이디를 받아서 해당 댓글을 삭제한다.
     */
    @Transactional
    public void deleteComment(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * 댓글 좋아요 메서드
     * 댓글 아이디와 회원 아이디를 받아서
     * 이미 남겨져 있지 않다면 해당 댓글의 좋아요를 남기고, 있다면 좋아요를 취소한다.
     * 자신의 댓글도 좋아요를 누를 수 있고, 자신의 댓글이 아니면 알람을 보낸다.
     * @return String
     */
    @Transactional
    public String likeComment(final Long commentId, final Long memberId) {

        final Comment findComment = findCommentWithMemberInfo(commentId);
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
        final Member loginMember = findMember(memberId);

        if (commentOwner == loginMember) {
            return CommentLike.makeCommentLike(loginMember, findComment);
        }

        return CommentLike.makeCommentLikeWithNotification(loginMember, findComment, makeCommentLikeNotification(loginMember, commentOwner));
    }

    private Member findCommentOwner(final Comment findComment) {
        return findComment.getPost().getMember();
    }

    private CommentLikeNotification makeCommentLikeNotification(final Member loginMember, final Member commentOwner) {
        return notificationRepository.save(CommentLikeNotification.from(loginMember, commentOwner));
    }

    /**
     * 댓글 상세 조회 메서드
     * 댓글 아이디를 받아서
     * 해당 댓글의 상세 정보를 조회한다.
     * @return CommentResDto
     */
    public CommentResDto findCommentDetail(final Long commentId) {
        return new CommentResDto(findComment(commentId));
    }

    //공용 메서드
    public Comment findComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new CommentNotFoundException(String.format("id=%s not found",commentId));
                });
    }

    public Comment findCommentWithPostInfo(final Long commentId) {
        return commentRepository.findWithPostByCommentId(commentId)
                .orElseThrow(() -> {
                    throw new CommentNotFoundException(String.format("id=%s not found",commentId));
        });
    }

    private Comment findCommentWithMemberInfo(final Long commentId) {
        return commentRepository.findCommentWithMemberInfo(commentId)
                .orElseThrow(() -> {
                    throw new CommentNotFoundException(String.format("id=%s not found",commentId));
                });
    }

    private Post findPostWithCommentInfo(final Long postId) {
        return postRepository.findWithCommentByPostId(postId)
                .orElseThrow(() -> {
                    throw new PostNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Post findPostWithMemberInfo(final Long postId) {
        return postRepository.findWithMemberAndCommentByPostId(postId)
                .orElseThrow(() -> {
                    throw new PostNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new MemberNotFoundException(String.format("id=%s not found",memberId));
                });
    }

}
