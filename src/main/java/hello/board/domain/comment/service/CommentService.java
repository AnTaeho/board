package hello.board.domain.comment.service;

import hello.board.controller.comment.dto.req.CommentUpdateDto;
import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.comment.repository.CommentLikeRepository;
import hello.board.domain.comment.repository.CommentRepository;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.MemberRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
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

    public List<CommentResDto> findAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    public List<CommentResDto> findCommentsByPost(Long postId) {
        Post findPost = findPost(postId);
        return findPost.getComments()
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResDto writeComment(Long postId, Long memberId, CommentWriteDto commentWriteDto) {
        Member commentMember = findMember(memberId);
        Comment newComment = new Comment(commentMember.getName(), commentWriteDto.getContent());
        System.out.println("newComment.id = " + newComment.getId());
        newComment.setPost(findPost(postId));
        return new CommentResDto(commentRepository.save(newComment));
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
        Comment findComment = findComment(commentId);

        if (commentLikeRepository.hasNoLike(commentId, memberId)) {
            commentLikeRepository.save(new CommentLike(findMember(memberId), findComment));
            return "Like success";
        } else {
            commentLikeRepository.deleteByCommentIdAndMemberId(commentId, memberId);
            return "Like deleted";
        }
    }

    public CommentResDto findCommentDetail(Long commentId) {
        return new CommentResDto(findComment(commentId));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("댓글이 이상해");
                });
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("게시글이 없어");
                });
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("회원이 없어");
                });
    }
}
