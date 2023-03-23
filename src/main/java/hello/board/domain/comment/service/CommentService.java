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
    public List<CommentResDto> findCommentsOfPost(Long postId) {
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
    public CommentResDto writeComment(Long postId, Member commentMember, CommentWriteDto writeDto) {
        Post findPost = findPostWithMemberInfo(postId);
        Comment newComment = Comment.makeComment(commentMember, writeDto.getContent(), findPost);
        if (isNotMyPost(commentMember, findPost)) {
            notificationRepository.save(makeCommentNotification(commentMember, findPost, newComment));
        }
        return new CommentResDto(commentRepository.save(newComment));
    }

    private boolean isNotMyPost(Member commentMember, Post findPost) {
        return !findPost.getMember().getId().equals(commentMember.getId());
    }

    private Notification makeCommentNotification(Member commentMember, Post findPost, Comment newComment) {
        Member notificatiedMember = findPost.getMember();
        return CommentNotification.from(commentMember.getName(), notificatiedMember, newComment);
    }

    @Transactional
    public CommentResDto writeChildComment(Long postId, Long commentId, Member commentMember, CommentWriteDto writeDto) {
        Post findPost = findPostWithCommentInfo(postId);
        Comment findComment = findComment(commentId);
        Comment newComment = Comment.makeChildComment(commentMember, writeDto.getContent(), findPost, findComment);
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
    public CommentResDto updateComment(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment findComment = findComment(commentId);
        findComment.updateInfo(commentUpdateDto.getContent());
        return new CommentResDto(findComment);
    }

    /**
     * 댓글 삭제 메서드
     * 댓글의 아이디를 받아서 해당 댓글을 삭제한다.
     */
    @Transactional
    public void deleteComment(Long commentId) {
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
    public String likeComment(Long commentId, Long memberId) {

        Comment findComment = findCommentWithMemberInfo(commentId);
        CommentLike notLiked = findLike(commentId, memberId);

        if (notLiked == null) {
            commentLikeRepository.save(pressCommentLike(memberId, findComment));
            return "Like success";
        } else {
            commentLikeRepository.delete(notLiked);
            return "Like deleted";
        }
    }

    private CommentLike findLike(Long commentId, Long memberId) {
        return commentLikeRepository.haveNoLike(commentId, memberId);
    }

    private CommentLike pressCommentLike(Long memberId, Comment findComment) {

        Member commentOwner = findCommentOwner(findComment);
        Member loginMember = findMember(memberId);

        if (commentOwner == loginMember) {
            return CommentLike.makeCommentLike(loginMember, findComment);
        }

        return CommentLike.makeCommentLikeWithNotification(loginMember, findComment, makeCommentLikeNotification(loginMember, commentOwner));
    }

    private Member findCommentOwner(Comment findComment) {
        return findComment.getPost().getMember();
    }

    private CommentLikeNotification makeCommentLikeNotification(Member loginMember, Member commentOwner) {
        return notificationRepository.save(CommentLikeNotification.from(loginMember, commentOwner));
    }

    /**
     * 댓글 상세 조회 메서드
     * 댓글 아이디를 받아서
     * 해당 댓글의 상세 정보를 조회한다.
     * @return CommentResDto
     */
    public CommentResDto findCommentDetail(Long commentId) {
        return new CommentResDto(findComment(commentId));
    }

    //공용 메서드
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",commentId));
                });
    }

    public Comment findCommentWithPostInfo(Long commentId) {
        return commentRepository.findCommentWithPostInfo(commentId)
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

    private Post findPostWithCommentInfo(Long postId) {
        return postRepository.findPostWithCommentInfo(postId)
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

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }

}
